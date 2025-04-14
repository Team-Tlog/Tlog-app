package com.tlog.viewmodel.travel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.tlog.data.model.travel.Review
import com.tlog.data.model.tmp.TmpTravelData

class TravelInfoViewModel: ViewModel() {
    // 임시 뷰모델 추천 화면 리스트에서 넘겨받는 용도로 사용할거임 추후 필수적으로 수정할 것

    private val _selectedTravelInfo = mutableStateOf<TmpTravelData>(
        TmpTravelData(
            travelName = "서귀포",
            description = "주도의 남쪽, 푸른 바다와 울창한 숲 사이에 자리한 서귀포는 ‘자연이 만든 힐링 도시’라 불릴 만한 곳입니다.높은 빌딩도, 시끄러운 소음도 없이 모든 게 천천히 흐르고, 그 속에서 여행자는 어느새 자신의 속도대로 걷게 됩니다.\n" +
                    "정방폭포와 천지연폭포처럼 시원하게 떨어지는 폭포는 도심 속에서는 느낄 수 없는 자연의 에너지로 가득하고,외돌개, 쇠소깍, 올레길 7코스를 걷다 보면 바다와 절벽, 숲이 한꺼번에 품어주는 풍경에 마음이 차분해져요.\n" +
                    "또 하나 빼놓을 수 없는 건 서귀포의 감성적인 거리들이에요.이중섭 거리에는 화가 이중섭의 삶과 예술이 고스란히 스며 있고,서귀포매일올레시장에서는 현지의 삶과 맛을 오롯이 느낄 수 있어요.\n" +
                    "서귀포는 빠르게 돌아보는 관광지가 아니라,느리게 머무르며 '나'를 돌아볼 수 있는 도시예요.제주 여행을 생각 중이라면, 서귀포에서 하루쯤은 꼭 머물러보길 추천해요.",
            hashTags = listOf("낭만", "바다", "섬"),
            cityName = "제주도",
            avgStarRating = 4.5,
            starRatings = listOf(
                0, 1, 2, 4, 13
            ),
            reviewList = listOf(
                Review(
                    author = "정찬",
                    createAt = "2025.04.01",
                    starRating = 5,
                    content = "혼자 떠난 첫 제주 여행이었는데, 서귀포는 혼자이기에 더 좋았던 도시였어요.정방폭포에서 시원한 물소리를 들으며 멍하니 앉아 있었고, 올레길 걷다 보니 어느새 마음도 같이 정리되더라고요. 이중섭 거리에서 조용히 전시도 보고, 시장에서는 현지인분들과 소소한 대화도 나눌 수 있어서 외롭지 않았어요." +
                            "숙소는 바다 뷰 있는 게스트하우스에 묵었는데, 호스트분이 추천해준 쇠소깍 카약 체험은 진짜 최고였어요! 렌터카 없이도 버스랑 도보로 충분히 다닐 수 있어서, 혼자 여행하시는 분들께 강력 추천합니다 :)" +
                            "서귀포는 조용히 나를 돌보고 싶을 때, 꼭 다시 오고 싶은 도시예요."
                ),
                Review(
                    author = "박신욱",
                    createAt = "2025.04.01",
                    starRating = 5,
                    content = "혼자 떠난 첫 제주 여행이었는데, 서귀포는 혼자이기에 더 좋았던 도시였어요.정방폭포에서 시원한 물소리를 들으며 멍하니 앉아 있었고, 올레길 걷다 보니 어느새 마음도 같이 정리되더라고요. 이중섭 거리에서 조용히 전시도 보고, 시장에서는 현지인분들과 소소한 대화도 나눌 수 있어서 외롭지 않았어요." +
                            "숙소는 바다 뷰 있는 게스트하우스에 묵었는데, 호스트분이 추천해준 쇠소깍 카약 체험은 진짜 최고였어요! 렌터카 없이도 버스랑 도보로 충분히 다닐 수 있어서, 혼자 여행하시는 분들께 강력 추천합니다 :)" +
                            "서귀포는 조용히 나를 돌보고 싶을 때, 꼭 다시 오고 싶은 도시예요."
                ),
                Review(
                    author = "서준우",
                    createAt = "2025.04.01",
                    starRating = 5,
                    content = "혼자 떠난 첫 제주 여행이었는데, 서귀포는 혼자이기에 더 좋았던 도시였어요.정방폭포에서 시원한 물소리를 들으며 멍하니 앉아 있었고, 올레길 걷다 보니 어느새 마음도 같이 정리되더라고요. 이중섭 거리에서 조용히 전시도 보고, 시장에서는 현지인분들과 소소한 대화도 나눌 수 있어서 외롭지 않았어요." +
                            "숙소는 바다 뷰 있는 게스트하우스에 묵었는데, 호스트분이 추천해준 쇠소깍 카약 체험은 진짜 최고였어요! 렌터카 없이도 버스랑 도보로 충분히 다닐 수 있어서, 혼자 여행하시는 분들께 강력 추천합니다 :)" +
                            "서귀포는 조용히 나를 돌보고 싶을 때, 꼭 다시 오고 싶은 도시예요."
                ),
                Review(
                    author = "백성수",
                    createAt = "2025.04.01",
                    starRating = 5,
                    content = "혼자 떠난 첫 제주 여행이었는데, 서귀포는 혼자이기에 더 좋았던 도시였어요.정방폭포에서 시원한 물소리를 들으며 멍하니 앉아 있었고, 올레길 걷다 보니 어느새 마음도 같이 정리되더라고요. 이중섭 거리에서 조용히 전시도 보고, 시장에서는 현지인분들과 소소한 대화도 나눌 수 있어서 외롭지 않았어요." +
                            "숙소는 바다 뷰 있는 게스트하우스에 묵었는데, 호스트분이 추천해준 쇠소깍 카약 체험은 진짜 최고였어요! 렌터카 없이도 버스랑 도보로 충분히 다닐 수 있어서, 혼자 여행하시는 분들께 강력 추천합니다 :)" +
                            "서귀포는 조용히 나를 돌보고 싶을 때, 꼭 다시 오고 싶은 도시예요."
                ),
                Review(
                    author = "고중수",
                    createAt = "2025.04.01",
                    starRating = 5,
                    content = "혼자 떠난 첫 제주 여행이었는데, 서귀포는 혼자이기에 더 좋았던 도시였어요.정방폭포에서 시원한 물소리를 들으며 멍하니 앉아 있었고, 올레길 걷다 보니 어느새 마음도 같이 정리되더라고요. 이중섭 거리에서 조용히 전시도 보고, 시장에서는 현지인분들과 소소한 대화도 나눌 수 있어서 외롭지 않았어요." +
                            "숙소는 바다 뷰 있는 게스트하우스에 묵었는데, 호스트분이 추천해준 쇠소깍 카약 체험은 진짜 최고였어요! 렌터카 없이도 버스랑 도보로 충분히 다닐 수 있어서, 혼자 여행하시는 분들께 강력 추천합니다 :)" +
                            "서귀포는 조용히 나를 돌보고 싶을 때, 꼭 다시 오고 싶은 도시예요."
                )
            )
    )
    )
    val selectedTravelInfo: State<TmpTravelData> = _selectedTravelInfo



    fun selectTravelInfo(info: TmpTravelData) {
        _selectedTravelInfo.value = info
    }




    // 일단 하나로 해두는데, 추후 나눠야 될 듯
    private val _sortOption = mutableStateOf("추천순")
    val sortOption: State<String> = _sortOption
    fun updateSelectOption(newSortOption: String) {
        _sortOption.value = newSortOption
    }
}