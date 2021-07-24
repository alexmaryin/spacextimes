package ru.alexmaryin.spacextimes_rx.data.api.remote

class SpacexUrls {
    companion object {
        const val BaseUrl = "https://api.spacexdata.com/v4/"
        const val CapsuleQuery = "capsules/query/"
        const val CoreQuery = "cores/query/"
        const val CrewQuery = "crew/query/"
        const val DragonQuery = "dragons/query/"
        const val LandingPadQuery = "landpads/query/"
        const val LaunchPadQuery = "launchpads/query/"
//        const val AllLaunchesQueryV4 = "launches/query/"
        const val AllLaunchesQuery = "https://api.spacexdata.com/v5/launches/query/"
        const val RocketQuery = "rockets/query/"
        const val HistoryQuery = "history/query/"
        const val PayloadQuery = "payloads/query/"
    }
}
