package com.etwoitwo.damda.feature.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.etwoitwo.damda.R
import com.etwoitwo.damda.databinding.FragmentSearchListBinding
import com.etwoitwo.damda.model.dataclass.Search

private const val ARG_PARAM1 = "param1"

class SearchListFragment : Fragment() {
    private var _binding: FragmentSearchListBinding? = null
    private val binding get() = _binding!!


    // TODO: Rename and change types of parameters
    private var param1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSearchListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(param1 == "gs") {
            val dataList = arrayListOf<Search>(
                Search("코스닥", "078930", "GS", 45000),
                Search("코스닥", "078935", "GS우", 37150),
                Search("코스피", "083450", "GST", 25550)
            )

            val adapter = SearchListAdapter(dataList)
            binding.recyclerViewSearch.adapter = adapter
        } else if(param1 == "저렴한 주식") {
            val dataList = arrayListOf<Search>(
                        Search("코스피", "032800", "판타지오"   ,106),
                        Search("코스피", "900110", "이스트아시아홀딩스"   ,163),
                        Search("코스피", "900120", "씨케이에이치"   ,215),
                        Search("코스닥", "096300", "베트남개발1"   ,238),
                        Search("코스피", "224060", "코디엠"   ,243),
                        Search("코스피", "900260", "로스웰"   ,298),
                        Search("코스닥", "004410", "서울식품"   ,323),
                        Search("코스피", "222810", "마이더스AI"   ,333),
                        Search("코스닥", "550043", "QV 인버스 레버리지 WTI원유 선물 ETN(H)"   ,345),
                        Search("코스닥", "500027", "신한 인버스 2X WTI원유 선물 ETN(H)"   ,380),
                        Search("코스닥", "530036", "삼성 인버스 2X WTI원유 선물 ETN"   ,385),
                        Search("코스닥", "093230", "이아이디"   ,403),
                        Search("코스피", "900280", "골든센츄리"   ,407),
                        Search("코스닥", "027740", "마니커"   ,446),
                        Search("코스피", "900270", "헝셩그룹"   ,467),
                        Search("코스피", "021880", "메이슨캐피탈"   ,576),
                        Search("코스닥", "145210", "다이나믹디자인"   ,483),
                        Search("코스피", "029480", "릭스솔루션"   ,493),
                        Search("코스닥", "007120", "미래아이앤지"   ,503),
                        Search("코스닥", "015590", "큐로"   ,499),
                        Search("코스피", "096040", "이트론"   ,517),
                        Search("코스피", "016600", "큐캐피탈"   ,510),
                        Search("코스피", "139050", "시티랩스"   ,537),
                        Search("코스피", "078650", "지나인제약"   ,567),
                        Search("코스피", "096350", "대창솔루션"   ,556),
                        Search("코스피", "036630", "세종텔레콤"   ,554),
                        Search("코스피", "051780", "큐로홀딩스"   ,605),
                        Search("코스피", "900300", "오가닉티코스메틱"   ,627),
                        Search("코스피", "019570", "리더스 기술투자"   ,635),
                        Search("코스닥", "005030", "부산주공"   ,707)
            )

            val adapter = SearchListAdapter(dataList)
            binding.recyclerViewSearch.adapter = adapter
        } else if(param1 == "커피 한 잔") {
            val dataList = arrayListOf<Search>(
                Search("코스닥", "000370","한화손해보험",4540),
                Search("코스닥", "000430","대원강업",4060),
                Search("코스닥", "001420","태원물산", 4900),
                Search("코스닥", "001570","금양",4940),
                Search("코스닥", "001780","알루코",4230),
                Search("코스피", "002230","피에스텍",4495),
                Search("코스닥", "003080","성보화학",4080),
                Search("코스닥", "003470","유안타증권",4150),
                Search("코스닥", "003475","유안타증권우",4190),
                Search("코스닥", "003480","한진중공업홀딩스",4710),
                Search("코스닥", "003520","영진약품",4765),
                Search("코스닥", "003530","한화투자증권",4515),
                Search("코스닥", "004415","서울식품우",4880),
                Search("코스닥", "004540","깨끗한나라",4600),
                Search("코스닥", "005360","모나미",4670),
                Search("코스닥", "00680K","미래에셋증권2우B",4780),
                Search("코스닥", "008260","NI스틸",4160),
                Search("코스닥", "008420","문배철강",4410),
                Search("코스닥", "008560","메리츠증권",4900),
                Search("코스닥", "009180","한솔로지스틱스",4255),
                Search("코스닥", "009580","무림P&P",4440),
                Search("코스닥", "010040","한국내화",4115),
                Search("코스닥", "011280","태림포장",4040),
                Search("코스닥", "011810","STX",4145),
                Search("코스닥", "012200","계양전기",4460),
                Search("코스닥", "014440","영보화학",4230),
                Search("코스닥", "014530","극동유화",4555),
                Search("코스피", "014940","오리엔탈정공",4040),
                Search("코스피", "016100","리더스코스메틱",4100),
                Search("코스닥", "016740","두올", 4950)
            )

            val adapter = SearchListAdapter(dataList)
            binding.recyclerViewSearch.adapter = adapter
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            SearchListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}