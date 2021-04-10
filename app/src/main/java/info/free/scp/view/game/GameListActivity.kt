package info.free.scp.view.game

import android.os.Bundle
import info.free.scp.R
import info.free.scp.util.EventUtil
import info.free.scp.view.base.BaseActivity
import kotlinx.android.synthetic.main.activity_game_list.*

class GameListActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_list)
        EventUtil.onEvent(this, EventUtil.clickGameList)
        baseToolbar = game_list_toolbar
        supportActionBar?.title = "游戏列表"
        vpGame?.adapter = GamePagerAdapter(this)
        tabGame?.setupWithViewPager(vpGame)
    }
}
