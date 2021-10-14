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

class StockListAdapter(private var list: MutableList<StockData.Data>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val GEN_STOCK_TYPE = 0
        const val TRANS_DAY_TYPE = 1
    }

    // ViewHolder에게 item을 보여줄 View로 쓰일 item_data_list.xml을 넘기면서 ViewHolder를 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // 언제? RecyclerView는 ViewHolder 새로 만들 떄마다 해당 메소드 호출.
        // 역할? ViewHolder와 그에 연결된 View를 생성, 초기화.
        // 주의? ViewHolder가 아직 특정 데이터에 바인딩된 상태가 아니기 때문에 뷰의 콘텐츠를 채우지는 않음.
        if (viewType == GEN_STOCK_TYPE){
            val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_main_stocklistitem, parent, false)
            return StockViewHolder(view)
        }
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_wallet_stocklistday, parent, false)
        return TransDayViewHolder(view)

    }

    override fun getItemCount(): Int {
        // 언제? 이 메서드를 사용해 항목을 추가로 표시할 수 없는 상황을 확인할 때 유용
        // 역할? 데이터 세트 크기 가져옴
        return list.count()
    }

    // ViewHolder의 bind 메소드를 호출한다.
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // 언제? ViewHolder를 데이터와 연결할 때 이 메서드를 호출합니다

        if (list[position].viewType == GEN_STOCK_TYPE){
            Log.d("ListAdapter view holder", "GEN STOCK TYPE")
            (holder as StockViewHolder).bind(list[position])
        } else {
            Log.d("ListAdapter view holder", "TRANS DAT TYPE")
            (holder as TransDayViewHolder).bind(list[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].viewType
    }

    private inner class TransDayViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val transDay: TextView = itemView.findViewById(R.id.txtview_wallet_transday)
        private val valLosMoney: TextView = itemView.findViewById(R.id.txtview_wallet_vallossmoney)

        private fun getColorInt(context:Context, value:Int): Int {
            return when {
                value > 0 -> {
                    ContextCompat.getColor(context, R.color.pastel_red)
                }
                value <0 -> {
                    ContextCompat.getColor(context, R.color.dark_sky_blue)
                }
                else -> {
                    ContextCompat.getColor(context, R.color.black)
                }
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(item: StockData.Data){
            if (item.transDayDate != null && item.transDayProfitLoss != null){
                Log.d("ListAdapter trans day", "===== ===== ===== ===== bind ===== ===== ===== =====")
                // TODO 날짜 제대로 넣기
                transDay.text = item.transDayDate

                val tDecUp = DecimalFormat("#,###")
                val sign = if (item.transDayProfitLoss > 0){"+"} else {""}

                valLosMoney.text = sign + tDecUp.format(item.transDayProfitLoss) + "원"
                valLosMoney.setTextColor(getColorInt(valLosMoney.context, item.transDayProfitLoss))
            }
        }
    }

    private inner class StockViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private val marketType : TextView = itemView.findViewById(R.id.txtview_main_markettype)
        private val stockCode: TextView = itemView.findViewById(R.id.txtview_main_stockcode)
        private val stockName: TextView = itemView.findViewById(R.id.txtview_main_stockname)
        private val transType: TextView = itemView.findViewById(R.id.txtview_wallet_transtype)
        private val transContent: TextView = itemView.findViewById(R.id.txtview_wallet_transcontent)
        private val transTime: TextView = itemView.findViewById(R.id.txtview_wallet_transtime)
        private val stockPrice: TextView = itemView.findViewById(R.id.txtview_main_stockprice)
        private val stockTotPrice: TextView = itemView.findViewById(R.id.txtview_main_stocktotprice)
        private val stockPercent: TextView = itemView.findViewById(R.id.txtview_main_stockpercent)

        private fun getColorInt(context:Context, value:Int): Int {
            return when {
                value > 0 -> {
                    ContextCompat.getColor(context, R.color.pastel_red)
                }
                value <0 -> {
                    ContextCompat.getColor(context, R.color.dark_sky_blue)
                }
                else -> {
                    ContextCompat.getColor(context, R.color.black)
                }
            }
        }
        private fun getColorDouble(context: Context, value:Double): Int {
            return when {
                value > 0 -> {
                    ContextCompat.getColor(context, R.color.pastel_red)
                    }
                value <0 -> {
                    ContextCompat.getColor(context, R.color.dark_sky_blue)
                    }
                else -> {
                    ContextCompat.getColor(context, R.color.black)
                }
            }
        }
        // onBindViewHolder의 역할 대신
        @SuppressLint("SetTextI18n")
        fun bind(item: StockData.Data){
            Log.d("ListAdapter general", "===== ===== ===== ===== bind ===== ===== ===== =====")
            val tDecUp = DecimalFormat("#,###")

            /* 공통 영역 */
            if (item.marketType != null && item.stockId != null && item.currentPrice != null){
                marketType.text = item.marketType
                stockCode.text = item.stockId
                stockName.text = item.stockName
                stockPrice.text = tDecUp.format(item.currentPrice) + "원"

                Log.d("ListAdapter", item.stockId +" "+item.stockName)
            }

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

            if (item.transPrice != null && item.transAmount != null && item.transTime != null && item.transType != null){
                /* 지갑-거래내역 영역 */
                transType.text = item.transType
                transContent.text = "체결가 "+tDecUp.format(item.transPrice)+"원("+item.transAmount.toString()+"주)"
                transTime.text = item.transTime
                if (item.transType == "매수"){
                    stockTotPrice.visibility = View.INVISIBLE
                    stockPercent.visibility = View.INVISIBLE
                } else {
                    if (item.transProfitLoss != null){
                        val sign = if (item.transProfitLoss > 0){"+"} else {""}
                        val textColor = getColorInt(stockTotPrice.context, item.transProfitLoss)
                        stockTotPrice.text = sign + tDecUp.format(item.transProfitLoss)+"원"
                        stockTotPrice.setTextColor(textColor)
                    }
                    if (item.transProfitLossRate != null){
                        val sign = if (item.transProfitLossRate > 0){"+"} else {""}
                        val textColor = getColorDouble(stockPercent.context, item.transProfitLossRate)
                        stockPercent.text = sign + String.format("%.2f", item.transProfitLossRate) + "%"
                        stockPercent.setTextColor(textColor)
                    }
                }
            }
            else {
                transContent.visibility = View.INVISIBLE
                transTime.visibility = View.INVISIBLE
                transType.visibility = View.INVISIBLE
            }

        }

    }

}

