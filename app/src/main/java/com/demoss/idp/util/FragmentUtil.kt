package com.demoss.idp.util

import androidx.fragment.app.Fragment
import org.jetbrains.anko.bundleOf

fun <T : Fragment> T.withArguments(vararg params: Pair<String, Any?>): T {
    arguments = bundleOf(*params)
    return this
}