package com.alexiscrack3.spinny.api

import org.koin.dsl.module

var servicesModule = module {
    single { ServicesFactory() }
}
