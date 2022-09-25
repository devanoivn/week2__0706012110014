package Data

import Array.ArrayHewan

class Ayam(nama: String, usia: Int, addimage: String) : ArrayHewan(nama, usia,addimage) {
    override fun mamam(): String {
        super.mamam()
        var toast = "Kamu kasi makan biji kepada ayam"

        return toast;
    }
    override fun suara(): String {
        super.suara()
        var toast = "Pekok Pekok Pekokkkkkkkkkk"

        return toast;
    }
}