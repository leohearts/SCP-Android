package info.free.scp.service

import com.jakewharton.retrofit2.converter.kotlinx.serialization.serializationConverterFactory
import info.free.scp.SCPConstants
import info.free.scp.SCPConstants.LATEST_CREATED
import info.free.scp.SCPConstants.LATEST_TRANSLATED
import info.free.scp.bean.ApiBean
import info.free.scp.bean.FeedModel
import info.free.scp.bean.ScpCollectionModel
import info.free.scp.bean.ScpModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.serialization.json.JSON
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory


/**
 * Created by Free on 2018/5/5.
 *
 */

class HttpManager {
    private val TAG = javaClass.name

    private val contentType = MediaType.parse("application/json")!!
    val json = JSON
    private val bmobRetrofit: Retrofit = Retrofit.Builder()
            .baseUrl(SCPConstants.BMOB_API_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(serializationConverterFactory(contentType, JSON))
            .build()
    private val feedRetrofit: Retrofit = Retrofit.Builder()
            .baseUrl(SCPConstants.FEED_API_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(serializationConverterFactory(contentType, JSON))
            .build()

    private val bmobApiService = bmobRetrofit.create(ApiService::class.java)
    private val feedApiService = feedRetrofit.create(ApiService::class.java)



    fun getAppConfig(handleConfig: (configList: List<ApiBean.ConfigResponse>) -> Unit) {
        bmobApiService.getAppConfig().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : BaseObserver<ApiBean.ApiListResponse<ApiBean.ConfigResponse>>() {
                    override fun onNext(t: ApiBean.ApiListResponse<ApiBean.ConfigResponse>) {
                       handleConfig(t.results)
                    }
                })
    }
//
    fun getAllScp(skip:Int, limit: Int, updateView: (eventList: List<ScpModel>) -> Unit) {
        bmobApiService.getAllScp(skip, limit).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : BaseObserver<ApiBean.ApiListResponse<ScpModel>>() {
                    override fun onNext(t: ApiBean.ApiListResponse<ScpModel>) {
                        updateView(t.results)
                    }

                    override fun onFail() {
                        updateView(emptyList())
                    }
                })
    }
    fun getAllCollection(updateView: (eventList: List<ScpCollectionModel>) -> Unit) {
        bmobApiService.getAllCollection().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : BaseObserver<ApiBean.ApiListResponse<ScpCollectionModel>>() {
                    override fun onNext(t: ApiBean.ApiListResponse<ScpCollectionModel>) {
                        updateView(t.results)
                    }

                    override fun onFail() {
                        updateView(emptyList())
                    }
                })
    }

    fun getPartDetail(skip:Int, limit: Int, download_type: Int, updateView: (eventList: List<ScpModel>) -> Unit) {
        val where = "{\"download_type\":$download_type}"
        bmobApiService.getPartDetail(skip, limit, where).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : BaseObserver<ApiBean.ApiListResponse<ScpModel>>() {
                    override fun onNext(t: ApiBean.ApiListResponse<ScpModel>) {
                        updateView(t.results)
                    }
                })
    }

    fun getDetail(skip:Int, limit: Int, updateView: (eventList: List<ScpModel>) -> Unit) {
        bmobApiService.getScpDetail(skip, limit).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : BaseObserver<ApiBean.ApiListResponse<ScpModel>>() {
                    override fun onNext(t: ApiBean.ApiListResponse<ScpModel>) {
                        updateView(t.results)
                    }
                })
    }

    fun getLatest(feedType: Int = LATEST_CREATED, pageIndex: Int = 1, updateView: (eventList: List<FeedModel>) -> Unit) {
        when (feedType) {
            LATEST_CREATED -> {
                feedApiService.getLatestCn(pageIndex).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object : BaseObserver<ApiBean.ApiListResponse<FeedModel>>() {
                            override fun onNext(t: ApiBean.ApiListResponse<FeedModel>) {
                                updateView(t.results)
                            }
                        })
            }
            LATEST_TRANSLATED -> {
                feedApiService.getLatestTranslated(pageIndex).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(object : BaseObserver<ApiBean.ApiListResponse<FeedModel>>() {
                            override fun onNext(t: ApiBean.ApiListResponse<FeedModel>) {
                                updateView(t.results)
                            }
                        })
            }
        }

    }


    companion object {
        val instance = HttpManager()
    }
}
