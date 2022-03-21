package com.etwoitwo.damda.feature.graph

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.etwoitwo.damda.databinding.ActivitySellingBinding

class SellingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySellingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySellingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var BinderRecentMoney = binding.textViewRecentMoney
        var BinderInputStock = binding.editTextInputStock
        BinderRecentMoney.setText("15000")

        var recentMoney : Int  = Integer.parseInt(BinderRecentMoney.text.toString())
        var totalMoney = 0
        var inputStock = 0

        var BinderResultMoney = binding.textViewResultMoney
        BinderResultMoney.setText("+500000")

        var storedStock = 100 // 현재 내가 가진 주식 개수 적어주기
        var restStock = 0
        var lack = ""

        // 주의 개수를 입력하면 실시간으로 총 가격이 변함
        BinderInputStock.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString() == ""){
                    inputStock = 0
                    totalMoney = 0
                    lack="남음"
                    restStock = storedStock
                    binding.textViewTotalMoney.setText("0")
                }
                else {
                    inputStock = Integer.parseInt(s.toString())
                    totalMoney = inputStock * recentMoney
                    binding.textViewTotalMoney.setText(totalMoney.toString())

                    if (storedStock > inputStock) {
                        restStock = storedStock - inputStock
                        lack = "남음"
                        changeColor("#000000")

                    }
                    else {
                        restStock = inputStock - storedStock
                        lack = "부족"
                        changeColor("#e6504f")
                    }
                }
                binding.textViewRestStock.setText(restStock.toString())
                binding.textViewLack.setText(lack)
            }
        })

        addNumberKeyboard()

        binding.buttonMaxSelling.setOnClickListener{
            binding.editTextInputStock.setText(storedStock.toString())
            binding.textViewRestStock.setText("0")
            totalMoney = inputStock * recentMoney
            binding.textViewTotalMoney.setText(totalMoney.toString())
            binding.textViewLack.setText(lack)
            changeColor("#000000")
        }
        binding.buttonSelling.setOnClickListener{
            binding.buttonSelling.setOnClickListener {
                val dialog = SellingCustomDialog()
                dialog.show(supportFragmentManager, "SellingCustomDialog")

            }
        }
        binding.buttonBack.setOnClickListener{
            val intent = Intent(this, GraphActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or
                    Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TOP
            )
            startActivity(intent)
            finish()
        }
    }

    fun changeColor(colorString: String){
        binding.textViewMoneyUnit3.setTextColor(Color.parseColor(colorString))
        binding.textViewLack.setTextColor(Color.parseColor(colorString))
        binding.textViewLeftParentheses2.setTextColor(Color.parseColor(colorString))
        binding.textViewRightParentheses2.setTextColor(Color.parseColor(colorString))
        binding.textViewRestStock.setTextColor(Color.parseColor(colorString))
    }

    fun addNumberKeyboard (){
        binding.buttonZero.setOnClickListener{
            binding.editTextInputStock.append("0")
        }

        binding.buttonOne.setOnClickListener{
            binding.editTextInputStock.append("1")
        }

        binding.buttonTwo.setOnClickListener{
            binding.editTextInputStock.append("2")
        }

        binding.buttonThree.setOnClickListener{
            binding.editTextInputStock.append("3")
        }

        binding.buttonFour.setOnClickListener{
            binding.editTextInputStock.append("4")
        }

        binding.buttonFive.setOnClickListener{
            binding.editTextInputStock.append("5")
        }

        binding.buttonSix.setOnClickListener{
            binding.editTextInputStock.append("6")
        }

        binding.buttonSeven.setOnClickListener{
            binding.editTextInputStock.append("7")
        }

        binding.buttonEight.setOnClickListener{
            binding.editTextInputStock.append("8")
        }

        binding.buttonNine.setOnClickListener{
            binding.editTextInputStock.append("9")
        }

        binding.buttonAllDelete.setOnClickListener{
            binding.editTextInputStock.setText(null)
        }
        
        binding.buttonBackspace.setOnClickListener{
                binding.editTextInputStock.setText(binding.editTextInputStock.text.toString().substring(0, binding.editTextInputStock.text.toString().length-1))
        }
    }
}