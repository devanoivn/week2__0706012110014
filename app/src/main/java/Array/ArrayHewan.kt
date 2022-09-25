package Array

import Data.Base
import android.os.Parcel
import android.os.Parcelable

open class ArrayHewan(var nama:String?, var usia:Int?, var deskripsi:String?, ) : Parcelable {
    constructor(membuat: Parcel) : this(membuat.readString(), membuat.readValue(Int::class.java.classLoader) as? Int, membuat.readString(),) {}
    var addimage: String = ""
    open fun suara(): String {
        return "a";
    }

    open fun mamam(): String {
        return "a";
    }
    override fun describeContents(): Int {
        return 0
    }
    override fun writeToParcel(membuat: Parcel, flags: Int) {
        membuat.writeString(nama)
        membuat.writeValue(usia)
        membuat.writeString(deskripsi)
    }

    companion object CREATOR : Parcelable.Creator<ArrayHewan> {
        override fun createFromParcel(parcel: Parcel): ArrayHewan {
            return ArrayHewan(parcel)
        }

        override fun newArray(size: Int): Array<ArrayHewan?> {
            return arrayOfNulls(size)
        }
    }
}