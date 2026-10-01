# MP3

## Navigation3

Điều hướng được gom trong 4 file tại `app/src/main/java/com/myapplication/navigation`:

- `AppRoute.kt`: route, tham số và danh sách tab `BottomNavItem`.
- `AppNavigator.kt`: API điều hướng, lệnh, implementation và cấu hình Hilt.
- `NavigationState.kt`: backstack từng tab, luồng ngoài tab và lưu/khôi phục state.
- `AppNavHost.kt`: ánh xạ route sang Composable qua `entryProvider`, nhận lệnh khi Activity RESUMED và hiển thị bằng `NavDisplay`.

State thuộc Compose gốc, được `rememberSerializable` lưu/khôi phục qua configuration change và process death. ViewModel từng màn chỉ gọi navigator, không giữ tham chiếu đến Activity hay backstack. Channel giữ lệnh đang chờ khi host tạm ngừng collect; các lệnh đang chờ không được lưu qua process death.

Mỗi lần mở màn tạo `AppBackStackEntry` với ID riêng. ID được lưu cùng route và dùng làm `NavEntry.contentKey`, nên hai lần mở cùng route có state UI và ViewModel riêng. Các entry decorator giữ `rememberSaveable` và ViewModel của tab chưa hiển thị; ViewModel được giải phóng khi entry bị xóa. Dữ liệu ViewModel cần khôi phục sau process death vẫn phải được lưu bằng `SavedStateHandle` hoặc repository.

### Thêm màn hình

1. Thêm route vào `AppRoute.kt`. Chỉ truyền ID hoặc dữ liệu nhỏ có thể serialize:

```kotlin
@Serializable
data class Detail(val itemId: String) : AppRoute
```

2. Thêm entry vào `appEntryProvider` trong `AppNavHost.kt`:

```kotlin
entry<AppRoute.Detail> { route ->
    DetailScreen(itemId = route.itemId)
}
```

3. Inject `AppNavigator` vào `@HiltViewModel`, rồi gọi trong `viewModelScope`:

```kotlin
viewModelScope.launch {
    navigator.navigateTo(AppRoute.Detail(itemId = id), singleTop = true)
}
```

Khi thêm màn con, chỉ cần thêm route và entry; logic navigator/backstack dùng lại như cũ. Nếu thêm tab, thêm một item vào `BottomNavItem.items` và đăng ký entry của route gốc.

Truyền tham số route từ entry vào Screen; Navigation3 không tự đưa các tham số đó vào `SavedStateHandle` của ViewModel.

### API và hành vi Back

```kotlin
navigator.navigateTo(AppRoute.Blank)
navigator.navigateUp()
navigator.selectTab(AppRoute.Library)
navigator.navigateTo(AppRoute.Blank, popUpToRoute = AppRoute.Home, inclusive = false)
navigator.replaceAll(AppRoute.Home)
```

- `navigateTo`: push màn con vào stack đang mở. `singleTop` bỏ qua nếu route trên cùng đã trùng. Route gốc tab sẽ chuyển tab khi đang trong luồng tab và không có `popUpToRoute`.
- `popUpToRoute`: tìm lần xuất hiện cuối trong stack hiện tại, xóa các entry phía trên; `inclusive` xóa cả entry đó. Nếu không tìm thấy thì giữ lịch sử và push như bình thường.
- `selectTab`: giữ nguyên lịch sử và state của các tab. Chỉ nhận route trong `BottomNavItem.items`.
- Back: pop màn con; tại gốc Library/Profile thì quay về stack Home; tại entry cuối cùng của Home thì Back hệ thống thoát Activity.
- `replaceAll`: reset mọi tab với ID entry mới, xóa lịch sử cũ. Nếu destination là route ngoài tab (ví dụ Login), mở một stack riêng và ẩn bottom bar. Back không quay về luồng cũ. Khi login xong, gọi `replaceAll(AppRoute.Home)`.

### Kiểm tra

```sh
./gradlew :app:assembleDebug :app:testDebugUnitTest --no-configuration-cache
```

`AppNavigationStateTest` kiểm tra Back, singleTop, popUpTo, giữ lịch sử tab, reset toàn bộ và serialization của route/ID entry. Kiểm tra lưu state Compose trên thiết bị nằm trong `AppNavigationRestorationTest`.

`AppNavigationUiTest` kiểm tra navigator qua ViewModel Hilt, chuyển tab, giữ màn con, Back hệ thống và Activity recreation trên app thật. Chạy các test thiết bị bằng `./gradlew :app:connectedDebugAndroidTest --no-configuration-cache` khi có emulator hoặc thiết bị kết nối.

Tham khảo API: [Lưu state Navigation3](https://developer.android.com/guide/navigation/navigation-3/save-state) và [nhiều backstack](https://developer.android.com/guide/navigation/navigation-3/recipes/multiple-backstacks).
