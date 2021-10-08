package com.etwoitwo.damda.feature.graph

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.etwoitwo.damda.databinding.ActivitySellingBinding

private lateinit var binding: ActivitySellingBinding

class SellingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySellingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var BinderRecentMoney = binding.textViewRecentMoney
        var BinderInputStock = binding.editTextInputStock
        BinderRecentMoney.setText("30000")

        var recentMoney : Int  = Integer.parseInt(BinderRecentMoney.text.toString())

        BinderInputStock.addTextChangedListener(object: TextWatcher {
            var totalMoney = 0
            var stock = 0
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString() == ""){
                    stock = 0
                    totalMoney = 0
                    binding.textViewTotalMoney.setText("0")
                }
                else {
                    stock = Integer.parseInt(s.toString())
                    totalMoney = stock * recentMoney
                    binding.textViewTotalMoney.setText(totalMoney.toString())
                }
            }
        })

        addnumberKeyboard()

        binding.buttonMaxSelling.setOnClickListener{
            // 최대로 매도
        }
        binding.buttonSelling.setOnClickListener{
            // 매도하기
        }
    }

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.editTextInputStock.windowToken, 0)
    }

    fun addnumberKeyboard (){
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
        
        binding.buttonBack.setOnClickListener{
            if ( binding.editTextInputStock.toString() == null ){
                binding.editTextInputStock.setText("0")
            }
            else{
                binding.editTextInputStock.setText(binding.editTextInputStock.text.toString().substring(0, binding.editTextInputStock.text.toString().length-1))
            }
        }
    }
}