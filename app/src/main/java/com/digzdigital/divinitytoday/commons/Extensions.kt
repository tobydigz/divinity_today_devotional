@file:JvmName("ExtensionsUtils")

package xyz.digzdigital.keddit.commons.extensions

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import xyz.digzdigital.keddit.R

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean =false): View = LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)

fun ImageView.loadImage(imageUrl: String){
    if (TextUtils.isEmpty(imageUrl))Picasso.with(context).load(R.mipmap.ic_launcher).into(this)
    else Picasso.with(context).load(imageUrl).into(this)
}