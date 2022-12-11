package org.pablodeafsapps.shortlyapp.urlshortening.domain.utils

import org.pablodeafsapps.shortlyapp.urlshortening.domain.model.Url

fun Url.isValid(): Boolean = value.isNotEmpty()
