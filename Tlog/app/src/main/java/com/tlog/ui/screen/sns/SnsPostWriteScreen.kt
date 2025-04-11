package com.tlog.ui.screen.sns

import android.Manifest
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.tlog.R
import com.tlog.ui.component.sns.RecentTravelCourseGroup
import com.tlog.ui.theme.MainColor
import com.tlog.viewmodel.sns.SnsPostViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.compose.ui.platform.LocalConfiguration
import com.tlog.ui.component.share.TextButtonTopBar

@Preview
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SnsPostWriteScreen(viewModel: SnsPostViewModel = viewModel()) {
    // 31 이하 / 32 이상 권한 요청이 달라서 다르게 표시
    val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    // 권한 상태 (갤러리)
    val permissionState = rememberPermissionState(permission = permission)

    // 갤러리 이미지를 가져오기 위한 코드
    val context = LocalContext.current
    var images by remember { mutableStateOf<List<Uri>>(emptyList()) }

    val coroutineScope = rememberCoroutineScope() // 코루틴 사용

    // 스크린 시작 시 1번만 실행 (갤러리 접근)
    LaunchedEffect(permissionState.status.isGranted) {
        if (permissionState.status.isGranted) {
            coroutineScope.launch(Dispatchers.IO) {
                val result = loadGalleryImages(context)
                images = result
            }
        } else {
            permissionState.launchPermissionRequest()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .windowInsetsPadding(WindowInsets.systemBars)
            .verticalScroll(rememberScrollState())
    ) {
        TextButtonTopBar(
            title = "게시글 작성",
            btnText = "다음",
            btnClickable = {
                Log.d("다음", "my click!!")
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        RecentTravelCourseGroup( // 최근 다녀온 코스
            modifier = Modifier
                .padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(26.dp))

        SelectedImageView() // 선택된 이미지 보여주는 부분

        galleryImageView( // 갤러리 사진 보여주는 부분
            images = images,
            modifier = Modifier
                .fillMaxSize())

    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SelectedImageView(viewModel: SnsPostViewModel = viewModel()) { // 비율만 맞으면 SNS와 써도 될 듯?
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(LocalConfiguration.current.screenWidthDp.dp) // max width 값 (정사각형 만들기 위함) -> 일단 피그마는 height가 더 길지만 정사각형으로 처리했음
            .background(Color(0xFFD9D9D9))
    ) {
        val size = viewModel.selectImages.value.size
        if (viewModel.selectImages.value.isNotEmpty()) {
            val pagerState =
                rememberPagerState(pageCount = { viewModel.selectImages.value.size })

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth(),
            ) { page ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {

                    AsyncImage(
                        model = viewModel.selectImages.value[page],
                        contentDescription = "선택한 사진",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
            }
        }
        Icon(
            painter = painterResource(R.drawable.ic_image_stack),
            contentDescription = "",
            tint = if (size > 1) MainColor else Color.Unspecified,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 12.dp, bottom = 8.dp)
                .size(42.dp)
        )
    }
}

@Composable
fun galleryImageView(
    viewModel: SnsPostViewModel = viewModel(),
    images: List<Uri>,
    modifier: Modifier = Modifier
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier
            .fillMaxWidth()
            .height(LocalConfiguration.current.screenHeightDp.dp)
    ) {
        items(images) { uri ->
            Image(
                painter = rememberAsyncImagePainter(uri),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .aspectRatio(1f)
                    .clickable {
                        viewModel.updateSelectImages(uri)
                    }
                    .border(
                        2.dp,
                        shape = RectangleShape,
                        color = if (viewModel.selectImagesIn(uri)) MainColor else Color.Unspecified
                    )
            )
        }
    }
}

fun loadGalleryImages(context: Context): List<Uri> { // 갤러리에서 이미지 불러오는 함수
    val images = mutableListOf<Uri>()
    val collection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    val projection = arrayOf(MediaStore.Images.Media._ID)

    context.contentResolver.query(
        collection,
        projection,
        null,
        null,
        "${MediaStore.Images.Media.DATE_ADDED} DESC"
    )?.use { cursor ->
        val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)

        while (cursor.moveToNext()) {
            val id = cursor.getLong(idColumn)
            val contentUri = ContentUris.withAppendedId(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                id
            )
            images.add(contentUri)
        }
    }

    return images
}