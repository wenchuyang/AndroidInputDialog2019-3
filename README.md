# AndroidInputDialog2019-3
## 修改App名称和图标
1. Icon: 在Android视图中，res -> mipmap -> ic_launcher下保存的是不同尺寸的图标，mdpi(48x48), hdpi(72x72), xhdpi(96x96), xxhdpi(144x144), xxxhdpi(192x192)。可以直接替换掉ic_launcher源文件。或者在AndroidManifest.xml文件中，修改icon和roundIcon为你的图标文件。
2. Name: 在AndroidManifest.xml文件中，修改label为你想要的AppName。需要注意的是这里说的修改最好是在对应的Value文件中修改，而不是直接把对应的值改为你需要的值。
## 添加button绑定事件
button可以绑定的事件有很多，这里用常见的click事件来举例，其它类推。
1. 直接在页面元素的Onclick后边找到你想要绑定的方法进行绑定（这个方法在Android官网的入门教程里边有）
2. 使用findViewById/@BindView和setOnclickListener绑定事件（这里介绍BindView）
```
public class MainActivity extends AppCompatActivity {
    @BindView(R.id.show_button) Button show_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ...
        ButterKnife.bind(this); //这一句不要忘了
        show_button.setOnClickListener(onClick);
    }
    public View.OnClickListener onClick= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.show_button:
                    ...
                    break;
            }
        }
    };
}
```
需要注意的是这里如果你使用BindView的话，实际上是在使用资源绑定框架ButterKnife，所以需要在build.gradle文件中添加依赖如下（版本号可能需要注意一下）
```
dependencies {
    implementation 'com.jakewharton:butterknife:8.6.0'
    annotationProcessor 'com.jakewharton:butterknife-compiler:8.6.0'
}
```
3. 使用ButterKnife的@OnClick直接进行绑定
与第二种相同的是一样需要添加依赖和绑定当前activity，使用方法如下：
```
@OnClick(R.id.show_button)
public boolean show(){
    ...
    return true;
}
```
如果需要同时为多个元素绑定onClick事件：
```
@OnClick( {R.id.show_button2, R.id.show_button3, R.id.show_button4} )
public boolean onViewClicked(View view) {
    switch (view.getId()) {
        case R.id.show_button2:
            Toast.makeText(this, "onclick1", Toast.LENGTH_SHORT).show();
            break;
        case R.id.show_button3:
            Toast.makeText(this, "onclick2", Toast.LENGTH_SHORT).show();
            break;
        case R.id.show_button4:
            Toast.makeText(this, "onclick3", Toast.LENGTH_SHORT).show();
            break;
    }
    return true;
}
```
## 自定义弹框样式
这里的弹框都是带EditText输入框的弹框。dialog样式定义在styles.xml文件中的`<style name="AlertDialog" parent=...>...item</style>`下。你可以在某一个item下使用`@style/xxx`为这个item定义style xxx。
1. 默认EditText
```
AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialog); //设置弹框样式
final EditText editText = new EditText(context);
builder.setView(editText);
builder.show();
```
2. 自定义EditText
```
AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialog); //设置弹框样式
LayoutInflater layoutInflater = LayoutInflater.from(context);
View promptView = layoutInflater.inflate(R.layout.dialog_input, null); //设置输入框样式
builder.setView(promptView);
builder.show()
```
自定义EditText中，你需要在layout下新建一个dialog_input.xml布局文件，设置这个EditText样式。
## 添加弹框的回调
