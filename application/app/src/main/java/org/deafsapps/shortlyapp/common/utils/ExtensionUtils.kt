package org.deafsapps.shortlyapp.common.utils

import org.deafsapps.shortlyapp.common.domain.model.Url

fun Url.isValid(): Boolean = value.isNotEmpty()
