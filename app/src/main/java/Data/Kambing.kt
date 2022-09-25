package Data

import Array.ArrayHewan

class Kambing(name: String, usia: Int, addimage: String) : ArrayHewan(name, usia, addimage) {
    override fun mamam(): String {
        super.mamam()
        var toast = "kamu kasi makan rumput kepada kambing"

        return toast;
    }
    override fun suara(): String {
        super.suara()
        var toast = "Mbekkkkkk"

        return toast;
    }

}