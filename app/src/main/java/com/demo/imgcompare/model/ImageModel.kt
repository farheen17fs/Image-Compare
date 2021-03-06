package com.demo.imgcompare.model

/*
 {
    "albumId": 1,
    "id": 1,
    "title": "accusamus beatae ad facilis cum similique qui sunt",
    "url": "https://via.placeholder.com/600/92c952",
    "thumbnailUrl": "https://via.placeholder.com/150/92c952"
  }
  */

data class ImageModel(
    val albumId: String?,
    val id: String?,
    val title: String?,
    val url: String?,
    val thumbnailUrl: String?
)