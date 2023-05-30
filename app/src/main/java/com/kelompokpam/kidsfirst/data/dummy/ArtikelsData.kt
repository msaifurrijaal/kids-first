package com.kelompokpam.kidsfirst.data.dummy

import com.kelompokpam.kidsfirst.R
import com.kelompokpam.kidsfirst.data.model.Artikel

object ArtikelsData {
    private val titles = arrayOf(
        "Bumil, Ini 4 Jenis Buah yang Sebaiknya Tidak Dikonsumsi",
        "Hati-hati, Proses Stunting Bisa Dimulai dari Kehamilan",
        "Yakin Si Kecil Sudah Cukup Minum Air Putih? Cek Kebutuhan Anak sesuai Usia",
        "Peran Ayah Terhadap Kecerdasan Anak Perempuan"
    )

    private val imgProfileUser = arrayOf(
        "https://randomuser.me/api/portraits/men/1.jpg",
        "https://randomuser.me/api/portraits/men/26.jpg",
        "https://randomuser.me/api/portraits/men/20.jpg",
        "https://randomuser.me/api/portraits/men/10.jpg"
    )

    private val artikelImages = intArrayOf(
        R.drawable.artikel1,
        R.drawable.artikel2,
        R.drawable.artikel3,
        R.drawable.artikel4
    )

    private val username = arrayOf(
        "Dr. Mukri",
        "Salsa Elizabeth",
        "Dr. Antique Aurellie",
        "Tony B."
    )

    val listData : ArrayList<Artikel>
        get() {
            val list = arrayListOf<Artikel>()
            for (position in titles.indices) {
                var artikel = Artikel()
                artikel.title = titles[position]
                artikel.imgProfileUser = imgProfileUser[position]
                artikel.photo = artikelImages[position]
                artikel.nameWriter = username[position]

                list.add(artikel)
            }
            return list
        }
}
