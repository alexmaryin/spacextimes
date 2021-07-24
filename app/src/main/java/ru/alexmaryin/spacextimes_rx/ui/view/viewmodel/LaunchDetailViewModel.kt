package ru.alexmaryin.spacextimes_rx.ui.view.viewmodel

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.alexmaryin.spacextimes_rx.R
import ru.alexmaryin.spacextimes_rx.data.SpacexDataRepository
import ru.alexmaryin.spacextimes_rx.data.api.translator.TranslatorApi
import ru.alexmaryin.spacextimes_rx.data.api.wiki.localizeWiki
import ru.alexmaryin.spacextimes_rx.data.model.Launch
import ru.alexmaryin.spacextimes_rx.data.model.common.HasStringId
import ru.alexmaryin.spacextimes_rx.data.model.ui_items.*
import ru.alexmaryin.spacextimes_rx.utils.Loading
import ru.alexmaryin.spacextimes_rx.utils.Result
import java.text.DateFormat
import javax.inject.Inject

@HiltViewModel
class LaunchDetailViewModel @Inject constructor(
    val state: SavedStateHandle,
    val repository: SpacexDataRepository,
    private val translator: TranslatorApi,
) : ViewModel() {

    private val launchState = MutableStateFlow<Result>(Loading)
    fun getLaunchState() = launchState.asStateFlow()

    fun loadLaunch() = viewModelScope.launch {
        translator.run {
            repository.getLaunchById(state.get("launchId") ?: "")
                .translateDetails()
                .localizeWiki<Launch>(state.get("locale") ?: "en")
                .collect { result -> launchState.emit(result) }
        }
    }

    fun composeDetails(res: Context, launch: Launch) = mutableListOf<HasStringId>().apply {
        with(launch) {

            images.filter { it.isNotBlank() }.apply {
                if (isNotEmpty()) add(CarouselItem(images = this, launchName = name))
            }

            add(
                OneLineItem2(
                    left = res.getString(R.string.launch_number_caption),
                    right = flightNumber.toString()
                )
            )

            staticFireDateUtc?.let {
                add(
                    OneLineItem2(
                        left = res.getString(R.string.static_fire_date_caption),
                        right = DateFormat.getDateInstance(DateFormat.LONG).format(it)
                    )
                )
            }

            add(
                OneLineItem2(
                    left = res.getString(R.string.launch_date_caption),
                    right = when {
                        toBeDetermined -> res.getString(R.string.to_be_determined_string)
                        notEarlyThan -> res.getString(R.string.not_early_string) + dateTrimmed(res)
                        else -> dateTrimmed(res)
                    }
                )
            )

            details?.let {
                add(
                    TwoStringsItem(
                        id = "details",
                        caption = res.getString(R.string.details_caption),
                        details = detailsRu ?: details
                    )
                )
            }

            if (payloads.isNotEmpty()) {
                add(RecyclerHeader(text = res.getString(R.string.payload_caption)))
                addAll(payloads)
            }

            cores.mapNotNull { it.core }.apply cores@{
                if (isNotEmpty()) {
                    add(RecyclerHeader(text = res.resources.getQuantityString(R.plurals.assigned_core_caption, cores.size)))
                    if (!upcoming) {
                        add(TwoStringsItem(
                            caption = res.getString(R.string.landing_attempts_caption),
                            details = buildString {
                                cores.forEachIndexed { index, core ->
                                    append("${this@cores[index].serial}: ")
                                    if (core.landingAttempt == true && core.landingSuccess != null) {
                                        appendLine(
                                            (if (core.landingSuccess) res.getString(R.string.landing_success_string)
                                            else res.getString(R.string.landing_fail_string)) + " (${core.landingType})"
                                        )
                                    } else appendLine(res.getString(R.string.no_landing_attempt_string))
                                }
                            }
                        ))
                    }
                    addAll(this)
                }
            }

            launchPad?.let {
                add(RecyclerHeader(text = res.getString(R.string.assigned_launch_pad_caption)))
                add(it)
            }

            if (capsules.isNotEmpty()) {
                add(RecyclerHeader(text = res.resources.getQuantityString(R.plurals.assigned_capsules_caption, capsules.size)))
                addAll(capsules)
            }

            if (crewFlight.isNotEmpty()) {
                add(RecyclerHeader(text = res.resources.getQuantityString(R.plurals.assigned_crew_caption, crewFlight.size)))
                addAll(crewFlight.map { it.member })
            }

            if (failures.isNotEmpty()) {
                add(RecyclerHeader(text = res.getString(R.string.failures_list_caption)))
                failures.forEach { failure ->
                    add(
                        TwoStringsItem(
                            caption = res.getString(R.string.failure_time_altitude_text, failure.time ?: 0, failure.altitude ?: 0),
                            details = res.getString(R.string.failure_reason_text, failure.reason)
                        )
                    )
                }
            }

            LinksItem(
                wiki = wikiLocale ?: wikipedia,
                youtube = links.webcast ?: links.youtubeId?.let { "https://www.youtube.com/watch?v=${it}" },
                redditCampaign = links.reddit?.campaign,
                redditLaunch = links.reddit?.launch,
                redditMedia = links.reddit?.media,
                redditRecovery = links.reddit?.recovery,
                pressKit = links.presskit,
                article = links.article
            ).apply {
                if (isNotEmpty) {
                    add(RecyclerHeader(text = res.getString(R.string.links_string)))
                    add(this)
                }
            }
        }
    }
}