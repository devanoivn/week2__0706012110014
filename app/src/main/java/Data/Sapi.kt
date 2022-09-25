package Data

import Array.ArrayHewan

class Sapi(name: String, usia: Int, addimage: String) : ArrayHewan(name, usia, addimage) {
    override fun mamam(): String {
        super.mamam()
        var toast = "Kamu kasi makan pohon kepada sapi"

        return toast;
    }
    override fun suara(): String {
        super.suara()
        var toast = "Mooowwwwwwww"

        return toast;
    }

}