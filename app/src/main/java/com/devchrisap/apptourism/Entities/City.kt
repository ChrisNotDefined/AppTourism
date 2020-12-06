package com.devchrisap.apptourism.Entities

class City(_id: Int, name: String, imageUrl: String, climate: String, budget: Double) {
    var _id = _id
    var name = name
    var imageUrl = imageUrl
    var climate = climate
    var budget = budget
    var isExpanded: Boolean = false
}