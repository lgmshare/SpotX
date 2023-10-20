package com.firstpoli.spotx.models

class BpCountData {
    var count: Int = 0
    var totalSystolic: Int = 0
    var totalDiastolic: Int = 0
    var totalPulse: Int = 0
    var systolic: Int = 0
    var diastolic: Int = 0
    var pulse: Int = 0

    fun averageCountSystolic(): Int {
        if (totalSystolic <= 0 || count <= 0) return 0
        return totalSystolic / count
    }

    fun averageCountDiastolic(): Int {
        if (totalDiastolic <= 0 || count <= 0) return 0
        return totalDiastolic / count
    }

    fun averageCountPulse(): Int {
        if (totalPulse <= 0 || count <= 0) return 0
        return totalPulse / count
    }

    fun clean() {
        count = 0
        totalSystolic = 0
        totalDiastolic = 0
        totalPulse = 0
        systolic = 0
        diastolic = 0
        pulse = 0
    }

}