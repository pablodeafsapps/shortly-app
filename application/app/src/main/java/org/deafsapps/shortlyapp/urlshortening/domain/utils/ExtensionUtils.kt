package org.deafsapps.shortlyapp.urlshortening.domain.utils

import org.deafsapps.shortlyapp.urlshortening.domain.model.Url

fun Url.isValid(): Boolean = value.isNotEmpty()
