package com.kasai.gourmet_search_app.view.callback

import com.kasai.gourmet_search_app.service.model.Gourmet

/**
 * @link onClick(Gourmet gourmet) 店舗詳細画面に移動
 */
interface GourmetClickCallback {
    fun onClick(gourmet: Gourmet.Results.Shop)
}
