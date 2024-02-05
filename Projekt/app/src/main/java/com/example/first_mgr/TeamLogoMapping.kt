package com.example.first_mgr

object TeamLogoMapping {
    // Create a mapping of team IDs to drawable resource names
    val teamIDMap = mapOf(
        1 to R.drawable.team_1,
        2 to R.drawable.team_2,
        3 to R.drawable.team_3,
        4 to R.drawable.team_4,
        5 to R.drawable.team_5,
        6 to R.drawable.team_6,
        7 to R.drawable.team_7,
        8 to R.drawable.team_8,
        9 to R.drawable.team_9,
        10 to R.drawable.team_10,
        11 to R.drawable.team_11,
        12 to R.drawable.team_12,
        13 to R.drawable.team_13,
        14 to R.drawable.team_14,
        15 to R.drawable.team_15,
        16 to R.drawable.team_16,
        17 to R.drawable.team_17,
        18 to R.drawable.team_18,
        19 to R.drawable.team_19,
        20 to R.drawable.team_20,
        21 to R.drawable.team_21,
        22 to R.drawable.team_22,
        23 to R.drawable.team_23,
        24 to R.drawable.team_24,
        25 to R.drawable.team_25,
        26 to R.drawable.team_26,
        27 to R.drawable.team_27,
        28 to R.drawable.team_28,
        29 to R.drawable.team_29,
        30 to R.drawable.team_30
    )

    // Add a function to get the drawable resource ID for a team ID
    fun getLogoResourceID(teamID: Int): Int {
        return teamIDMap[teamID] ?: R.drawable.ic_menu_camera
    }
}
