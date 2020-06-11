package com.example.zvent.models
/**
 * <h1>Guest</h1>
 *<p>
 * Data class that contains the template of a guest
 *</p>
 *
 * @author José Ovando
 * @version 1.0
 **/
data class Guest(var name: String = "", var phone: String = "1234-4332",
                 var email: String = "example@example.com", var registered: String = "no" )