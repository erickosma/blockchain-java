object Timer {
    private var startTime: Long = 0
    private var stopTime: Long = 0
    private var elapsedTimeInSecond: Long = 0

    fun stat(){
        startTime = System.currentTimeMillis()
    }

    fun end(){
        stopTime = System.currentTimeMillis()
        elapsedTimeInSecond = (stopTime - startTime)

        println("-----------------------------------------")
        println("$elapsedTimeInSecond ml")
    }
}