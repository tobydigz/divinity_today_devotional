package com.digzdigital.divinitytoday.ui.base

/**
 * Base presenter.
 *
 * @param <V> - View
 */

interface BasePresenter<V>{
    fun onAttach(view: V)

    fun onDetach()
}