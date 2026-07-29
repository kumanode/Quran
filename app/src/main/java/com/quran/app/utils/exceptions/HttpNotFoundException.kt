/*
 * Copyright (c) Faisal Khan (https://github.com/faisalcodes)
 * Created on 1/4/2022.
 * All rights reserved.
 */
package com.quran.app.utils.exceptions

class HttpNotFoundException @JvmOverloads constructor(msg: String? = "Not found") : RuntimeException(msg)