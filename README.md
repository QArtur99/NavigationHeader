# Navigation Header
[![](https://jitpack.io/v/QArtur99/NavigationHeader.svg)](https://jitpack.io/#QArtur99/NavigationHeader)

Navigation Header inspired by [dribble menu](https://dribbble.com/shots/2653519-Menu) built with MotionLayout and ObjectAnimator.

### Screenshots
![v1](https://user-images.githubusercontent.com/25232443/60921704-777fb700-a29b-11e9-8623-9e08deaf0785.gif)
![h1](https://user-images.githubusercontent.com/25232443/60921712-7c446b00-a29b-11e9-8d6b-d8c25b7c458c.gif)

### Setup
Add it in project root build.gradle:
```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

Add the dependency in app build.gradle:
```
	dependencies {
	        ...
	        implementation 'com.github.QArtur99:NavigationHeader:1.0.1'
	}
```

### Usage
You need to include child layout inside parent layout (ConstraintLayout, RelativeLayout):
```
    <com.artf.navigationheader.NavigationHeaderLayout
            android:id="@+id/navigationHeader"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:theme="@style/ThemeOverlay.MaterialComponents.Dark.ActionBar"
            app:headerHeight="?attr/actionBarSize"
            app:baseElevation="6dp"
            app:animationDuration="500"
            app:layoutDescription="@xml/motion_scene"/>
```
#### Attributes
* `app:headerHeight` - header size on collapse.
* `app:baseElevation` - elevation of header on collapse. (optional)
* `app:animationDuration` - duration of animation on state change. (optional)
* `app:layoutDescription` - motion scene for Layout.

Create empty MotionScene in res/xml
```
            <MotionScene/>
```

#### In activity file
Create Header (statusBar and content colors are optional)
```
        val titleList = mutableListOf<Header>().apply {
            add(Header(title1, R.color.header1, R.color.statusBar1, R.color.content1))
            add(Header(title2, R.color.header2, R.color.statusBar2, R.color.content2))
            ...
        }
```

Create HeaderView
```
        titleList.forEach {
            val headerView = layoutInflater.inflate(R.layout.header, null)
            headerView.tag = it.title
            headerView.title.text = it.title
            headerView.setBackgroundColor(ContextCompat.getColor(this, it.headerColor!!))
            headerList.add(HeaderView(headerView, it.headerColor, it.statusBarColor, it.contentColor))
        }
```

Create List of content views (optional)
```
        val contentList = mutableListOf<View>().apply {
            add(c1)
            add(c2)
            ...
        }
```

Create listeners
```
        navigationHeader.setOnCollapseListener {
            when(it.tag){
                title1 -> {}
                title2 -> {}
                ...
            }
        }
```

Init NavigationHeader
```
        navigationHeader.initNavigationHeader(this, headerList, contentList)
```

#### Extra settings
Arrow color
```
        navigationHeader.arrow.setColorFilter(Color.BLACK)
```

Arrow invisible
```
        navigationHeader.arrow.setImageDrawable(null)
```

#### For more information see simple app included in this [repo](https://github.com/QArtur99/NavigationHeader/tree/master/exampleNavigationHeader)

### Features
* ActionBar stack menu animation with MotionLayout.
* Menu element elevation animation with ObjectAnimator.
* Menu arrow fade in/out animation with ObjectAnimator.
* StatusBar color animation with ObjectAnimator.
* Content elements color animation with ObjectAnimator
* Menu element ripple effect.

### How to run the project in development mode
* Clone or download repository as a zip file.
* Open project in Android Studio.
* Run 'app' `SHIFT+F10`.

### Report issues
Something not working quite as expected? Do you need a feature that has not been implemented yet? Check the [issue tracker](https://github.com/QArtur99/NavigationHeader/issues) and add a new one if your problem is not already listed. Please try to provide a detailed description of your problem, including the steps to reproduce it.


### License
Navigation Header is released under the MIT license. See [LICENSE](./LICENSE) for details.