package com.devchrisap.apptourism.Entities

class Destiny(
    var _id: String,
    var score: Float,
    var image: String,
    var description: String,
    var cityId: Int,
    var name: String,
    var isExpanded: Boolean = false
) {

}