package com.etwoitwo.damda.feature.graph

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.etwoitwo.damda.R
import com.etwoitwo.damda.databinding.ActivityBuyingBinding
import com.etwoitwo.damda.feature.search.SearchListActivity

private lateinit var binding: ActivityBuyingBinding

class BuyingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuyingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var BinderRecentMoney = binding.textViewRecentMoney
        var BinderInputStock = binding.editTextInputStock
        BinderRecentMoney.setText("45,000")

        var recentMoney: Int = 45000
        var totalMoney = 0
        var inputStock = 0

        var storedMoney = 400000 // 현재 내가 가진 잔액 적어주기 !! 서버 연결 !!
        var BinderResultMoney = binding.textViewTotalMoney
        BinderResultMoney.setText(storedMoney.toString())

        var restMoney = 0
        binding.textViewLack.setText("남음")
        changeColor("#000000")
        var lack = ""

        // 주의 개수를 입력하면 실시간으로 총 가격이 변함
        BinderInputStock.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString() == "") {
                    inputStock = 0
                    totalMoney = 0
                    lack = "남음"
                    restMoney = storedMoney
                    binding.textViewTotalMoney.setText("0")
                } else {
                    inputStock = Integer.parseInt(s.toString())
                    totalMoney = inputStock * recentMoney
                    binding.textViewTotalMoney.setText(totalMoney.toString())

                    if (storedMoney > totalMoney) {
                        restMoney = storedMoney - totalMoney
                        lack = "남음"
                        changeColor("#000000")

                    } else {
                        restMoney = storedMoney - totalMoney
                        lack = "부족"
                        changeColor("#e6504f")
                    }
                }
                binding.textViewRestMoney.setText(restMoney.toString())
                binding.textViewLack.setText(lack)
            }
        })

        addNumberKeyboard()

        binding.buttonMaxBuying.setOnClickListener {
            var maxStock = storedMoney / recentMoney
            restMoney = storedMoney - totalMoney
            lack = "남음"
            Log.d("maxStock", maxStock.toString())
            binding.editTextInputStock.setText(maxStock.toString())
            binding.textViewTotalMoney.setText(totalMoney.toString())
            binding.textViewRestMoney.setText(restMoney.toString())
            binding.textViewLack.setText(lack)
        }
        binding.buttonBuying.setOnClickListener {
            val dialog = BuyingCustomDialog()
            dialog.show(supportFragmentManager, "BuyingCustomDialog")

        }
        binding.buttonBack.setOnClickListener{
            val Intent = Intent(this, GraphActivity::class.java)
            startActivity(Intent)
        }
    }

    fun changeColor(colorString: String) {
        binding.textViewMoneyUnit3.setTextColor(Color.parseColor(colorString))
        binding.textViewLack.setTextColor(Color.parseColor(colorString))
        binding.textViewLeftParentheses.setTextColor(Color.parseColor(colorString))
        binding.textViewRightParentheses.setTextColor(Color.parseColor(colorString))
        binding.textViewRestMoney.setTextColor(Color.parseColor(colorString))
    }

    fun addNumberKeyboard() {
        binding.buttonZero.setOnClickListener {
            binding.editTextInputStock.append("0")
        }

        binding.buttonOne.setOnClickListener {
            binding.editTextInputStock.append("1")
        }

        binding.buttonTwo.setOnClickListener {
            binding.editTextInputStock.append("2")
        }

        binding.buttonThree.setOnClickListener {
            binding.editTextInputStock.append("3")
        }

        binding.buttonFour.setOnClickListener {
            binding.editTextInputStock.append("4")
        }

        binding.buttonFive.setOnClickListener {
            binding.editTextInputStock.append("5")
        }

        binding.buttonSix.setOnClickListener {
            binding.editTextInputStock.append("6")
        }

        binding.buttonSeven.setOnClickListener {
            binding.editTextInputStock.append("7")
        }

        binding.buttonEight.setOnClickListener {
            binding.editTextInputStock.append("8")
        }

        binding.buttonNine.setOnClickListener {
            binding.editTextInputStock.append("9")
        }

        binding.buttonAllDelete.setOnClickListener {
            binding.editTextInputStock.setText(null)
        }

        binding.buttonBackspace.setOnClickListener {
            binding.editTextInputStock.setText(
                    binding.editTextInputStock.text.toString()
                        .substring(0, binding.editTextInputStock.text.toString().length - 1)
            )
        }
    }
}
