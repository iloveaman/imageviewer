package com.github.iielse.imageviewer

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.github.iielse.imageviewer.core.*

class ImageViewerBuilder(private val context: Context?,
                         private val imageLoader: ImageLoader,
                         private val dataProvider: DataProvider,
                         private val transformer: Transformer,
                         private val initKey: Long = 0
) {
    private var vhCustomizer: VHCustomizer? = null
    private var viewerCallback: ViewerCallback? = null
    private var overlayCustomizer: OverlayCustomizer? = null

    fun setVHCustomizer(vhCustomizer: VHCustomizer): ImageViewerBuilder {
        this.vhCustomizer = vhCustomizer
        return this
    }

    fun setViewerCallback(viewerCallback: ViewerCallback): ImageViewerBuilder {
        this.viewerCallback = viewerCallback
        return this
    }

    fun setOverlayCustomizer(overlayCustomizer: OverlayCustomizer?): ImageViewerBuilder {
        this.overlayCustomizer = overlayCustomizer
        return this
    }

    private fun create(): ImageViewerDialogFragment {
        return ImageViewerDialogFragment()
    }

    fun show() {
        if (Components.working) return
        (context as? FragmentActivity?)?.let {
            Components.initialize(imageLoader, dataProvider, transformer, initKey)
            Components.setVHCustomizer(vhCustomizer)
            Components.setViewerCallback(viewerCallback)
            Components.setOverlayCustomizer(overlayCustomizer)
            val viewer = create()
            Components.attach(viewer)
            viewer.show(it.supportFragmentManager)
        }
    }

}