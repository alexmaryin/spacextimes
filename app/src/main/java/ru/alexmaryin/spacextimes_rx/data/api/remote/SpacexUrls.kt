package ru.alexmaryin.spacextimes_rx.data.api.remote

class SpacexUrls {
    companion object {
        const val Base = "https://api.spacexdata.com/v4/"
        const val CapsuleQuery = "capsules/query/"
        const val CoreQuery = "cores/query/"
        const val CrewQuery = "crew/query/"
        const val AllDragons = "dragons/"
        const val AllLandingPads = "landpads/"
        const val LandingPadQuery = "landpads/query/"
        const val AllLaunchPads = "launchpads/"
        const val LaunchPadQuery = "launchpads/query/"
        const val AllLaunchesQuery = "launches/query/"
        const val AllRockets = "rockets/"
        const val AllHistoryEvents = "history/"
        const val PayloadQuery = "payloads/query/"
    }
}
