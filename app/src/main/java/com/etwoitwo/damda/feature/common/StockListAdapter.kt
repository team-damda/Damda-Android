package com.etwoitwo.damda.feature.common

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.etwoitwo.damda.R
import com.etwoitwo.damda.model.dataclass.StockData
import java.text.DecimalFormat
import kotlin.reflect.typeOf

class StockListAdapter(private var list: MutableList<StockData.Data>): RecyclerView.Adapter<StockListAdapter.ViewHolder>() {

    // ViewHolder에게 item을 보여줄 View로 쓰일 item_data_list.xml을 넘기면서 ViewHolder를 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // 언제? RecyclerView는 ViewHolder 새로 만들 떄마다 해당 메소드 호출.
        // 역할? ViewHolder와 그에 연결된 View를 생성, 초기화.
        // 주의? ViewHolder가 아직 특정 데이터에 바인딩된 상태가 아니기 때문에 뷰의 콘텐츠를 채우지는 않음.
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_main_stocklistitem, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        // 언제? 이 메서드를 사용해 항목을 추가로 표시할 수 없는 상황을 확인할 때 유용
        // 역할? 데이터 세트 크기 가져옴
        var size: Int = 0
        size = list.count()
        return size
    }

    // ViewHolder의 bind 메소드를 호출한다.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // 언제? ViewHolder를 데이터와 연결할 때 이 메서드를 호출합니다
        Log.d("ListAdapter", "===== ===== ===== ===== onBindViewHolder ===== ===== ===== =====")
        holder.bind(list[position])
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val marketType : TextView = itemView.findViewById(R.id.txtview_main_markettype)
        private val stockCode: TextView = itemView.findViewById(R.id.txtview_main_stockcode)
        private val stockName: TextView = itemView.findViewById(R.id.txtview_main_stockname)
        private val transContent: TextView = itemView.findViewById(R.id.txtview_wallet_transcontent)
        private val transTime: TextView = itemView.findViewById(R.id.txtview_wallet_transtime)
        private val stockPrice: TextView = itemView.findViewById(R.id.txtview_main_stockprice)
        private val stockTotPrice: TextView = itemView.findViewById(R.id.txtview_main_stocktotprice)
        private val stockPercent: TextView = itemView.findViewById(R.id.txtview_main_stockpercent)

        fun getColorInt(context:Context, value:Int): Int {
            return if (value > 0){
                ContextCompat.getColor(context, R.color.pastel_red)
            } else if(value <0){
                ContextCompat.getColor(context, R.color.dark_sky_blue)
            } else {
                ContextCompat.getColor(context, R.color.black)
            }
        }
        fun getColorDouble(context: Context, value:Double): Int {
            return if (value > 0){
                ContextCompat.getColor(context, R.color.pastel_red)
            } else if(value <0){
                ContextCompat.getColor(context, R.color.dark_sky_blue)
            } else {
                ContextCompat.getColor(context, R.color.black)
            }
        }

        // onBindViewHolder의 역할 대신
        @SuppressLint("SetTextI18n")
        fun bind(item: StockData.Data){
            Log.d("ListAdapter", "===== ===== ===== ===== bind ===== ===== ===== =====")
            Log.d("ListAdapter", item.stockId.toString()+" "+item.stockName)

            /* 공통 영역 */
            marketType.text = item.marketType
            stockCode.text = item.stockId
            stockName.text = item.stockName
            val tDecUp = DecimalFormat("#,###")
            stockPrice.text = tDecUp.format(item.currentPrice) + "원"

            /* 메인-관심종목 영역 */
            if (item.todayChange != null ){
                val triangle = if (item.todayChange > 0){"▲ "} else if(item.todayChange <0){"▼ "} else {""}
                val textColor = getColorInt(stockTotPrice.context, item.todayChange)
                stockTotPrice.setTextColor(textColor)
                stockTotPrice.text = triangle + item.todayChange.toString() + "원"

            }
            if (item.todayRoC != null){
                val sign = if (item.todayRoC > 0){"+"} else {""}
                val textColor = getColorDouble(stockPercent.context, item.todayRoC)
                stockPercent.text = sign + String.format("%.2f", item.todayRoC) + "%"
                stockPercent.setTextColor(textColor)
            }

            /* 공통-보유종목 영역 */
            if (item.totProfitLoss != null && item.totCnt != null){
                val sign = if (item.totProfitLoss > 0){"+"} else {""}
                val textColor = getColorInt(stockTotPrice.context, item.totProfitLoss)
                stockTotPrice.setTextColor(textColor)
                stockTotPrice.text = sign + tDecUp.format(item.totProfitLoss) + "원(${item.totCnt}주)"
            }
            if (item.totProfitLossRate != null){
                val sign = if (item.totProfitLossRate > 0){"+"} else {""}
                val textColor = getColorDouble(stockPercent.context, item.totProfitLossRate)
                stockPercent.setTextColor(textColor)
                stockPercent.text = sign + String.format("%.2f", item.totProfitLossRate) + "%"
            }
            // TODO null 물어보고 조건문 안에 넣어주기
            transContent.visibility = View.INVISIBLE
            transTime.visibility = View.INVISIBLE

        }
    }

}

