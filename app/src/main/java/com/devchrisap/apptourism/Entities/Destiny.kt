package com.devchrisap.apptourism.Entities

class Destiny(
    var _id: String,
    var score: Double,
    var image: String,
    var description: String,
    var cityId: Int,
    var name: String,
    var isExpanded: Boolean = false
) {

}