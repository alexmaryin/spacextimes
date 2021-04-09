package ru.alexmaryin.spacextimes_rx.data.repository

import android.util.Log
import ru.alexmaryin.spacextimes_rx.data.model.*
import ru.alexmaryin.spacextimes_rx.data.model.lists.*
import javax.inject.Inject

class ApiLocalImpl @Inject constructor(): ApiLocal {

    override fun getCapsules(): List<Capsules> {
        TODO("Not yet implemented")
    }

    override fun saveCapsules(capsules: List<Capsules>) {
        TODO("Not yet implemented")
    }

    override fun getCapsuleById(id: String): Capsule? {
        Log.d("REPO_LOCAL", "Local capsule with id $id is requested. Return null for debug.")
        return null
    }

    override fun getCores(): List<Core> {
        TODO("Not yet implemented")
    }

    override fun getCoreById(id: String): Core? {
        Log.d("REPO_LOCAL", "Local core with id $id is requested. Return null for debug.")
        return null
    }

    override fun getCrew(): List<Crews> {
        TODO("Not yet implemented")
    }

    override fun getCrewById(id: String): Crew? {
        Log.d("REPO_LOCAL", "Local crew with id $id is requested. Return null for debug.")
        return null
    }

    override fun getDragons(): List<Dragon> {
        TODO("Not yet implemented")
    }

    override fun getDragonById(id: String): Dragon? {
        Log.d("REPO_LOCAL", "Local dragon with id $id is requested. Return null for debug.")
        return null
    }

    override fun getLaunchPads(): List<LaunchPads> {
        TODO("Not yet implemented")
    }

    override fun getLaunchPadById(id: String): LaunchPad? {
        Log.d("REPO_LOCAL", "Local launch pad with id $id is requested. Return null for debug.")
        return null
    }

    override fun getLandingPads(): List<LandingPads> {
        TODO("Not yet implemented")
    }

    override fun getLandingPadById(id: String): LandingPad? {
        Log.d("REPO_LOCAL", "Local landing pad with id $id is requested. Return null for debug.")
        return null
    }

    override fun getRockets(): List<Rocket> {
        TODO("Not yet implemented")
    }

    override fun getRocketById(id: String): Rocket? {
        Log.d("REPO_LOCAL", "Local rocket with id $id is requested. Return null for debug.")
        return null
    }

    override fun getLaunches(): List<Launches> {
        TODO("Not yet implemented")
    }

    override fun getLaunchById(id: String): Launch? {
        Log.d("REPO_LOCAL", "Local launch with id $id is requested. Return null for debug.")
        return null
    }

    override fun getPayloads(): List<Payload> {
        TODO("Not yet implemented")
    }

    override fun getPayloadById(id: String): Payload? {
        TODO("Not yet implemented")
    }

    override fun getHistoryEvents(): List<History> {
        TODO("Not yet implemented")
    }

    override fun getEventById(id: String): History? {
        TODO("Not yet implemented")
    }
}