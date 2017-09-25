# ViewStubDemo
使用的时候的注意事项：
http://www.jianshu.com/p/175096cd89ac

一 、某些布局属性要加在ViewStub而不是实际的布局上面，才会起作用，比如上面用的android:layout_margin*系列属性，如果加在 TextView上面，则不会起作用，需要放在它的ViewStub上面才会起作用。而ViewStub的属性在inflate()后会都传给相应的布 局。

 
    1.ViewStub之所以常称之为“延迟化加载”，是因为在教多数情况下，程序 无需显示ViewStub所指向的布局文件，只有在特定的某些较少条件下，此时ViewStub所指向的布局文件才需要被inflate，且此布局文件直 接将当前ViewStub替换掉，具体是通过viewStub.infalte()或 viewStub.setVisibility(View.VISIBLE)来完成；

    2.正确把握住ViewStub的应用场景非常重要，正如如1中所描述需求场景下，使用ViewStub可以优化布局；

    3.对ViewStub的inflate操作只能进行一次，因为inflate的 时候是将其指向的布局文件解析inflate并替换掉当前ViewStub本身（由此体现出了ViewStub“占位符”性质），一旦替换后，此时原来的 布局文件中就没有ViewStub控件了，因此，如果多次对ViewStub进行infalte，会出现错误信息：ViewStub must have a non-null ViewGroup viewParent。

    4.中所讲到的ViewStub指向的布局文件解析inflate并替换掉当前 ViewStub本身，并不是完全意义上的替换（与include标签还不太一样），替换时，布局文件的layout params是以ViewStub为准，其他布局属性是以布局文件自身为准。

    5.ViewStub本身是不可见的，对 ViewStub setVisibility(..)与其他控件不一样，ViewStub的setVisibility 成View.VISIBLE或INVISIBLE如果是首次使用，都会自动inflate其指向的布局文件，并替换ViewStub本身，再次使用则是相 当于对其指向的布局文件设置可见性。
    
    
二、viewStub中加载layout的代码：

    public View inflate() {  
        final ViewParent viewParent = getParent();// 获取当前view的父view，用于获取需要加载的layout的index  
      
        if (viewParent != null && viewParent instanceof ViewGroup) {  
            if (mLayoutResource != 0) {  
                final ViewGroup parent = (ViewGroup) viewParent;  
                final LayoutInflater factory;  
                if (mInflater != null) {  
                    factory = mInflater;  
                } else {  
                    factory = LayoutInflater.from(mContext);  
                }  
                final View view = factory.<strong><span style="font-size:14px;color:#ff0000;">inflate</span></strong>(mLayoutResource, parent, false);// 获取需要加载的layout  
      
                if (mInflatedId != NO_ID) {  
                    view.setId(mInflatedId);  
                }  
      
                final int index = parent.indexOfChild(this);  
                parent.removeViewInLayout(this);// 删除之前加载的view  
      
                final ViewGroup.LayoutParams layoutParams = getLayoutParams();  
                if (layoutParams != null) {  
                    parent.addView(view, index, layoutParams);  
                } else {  
                    parent.<strong><span style="font-size:14px;color:#ff0000;">addView</span></strong>(view, index);// 添加view  
                }  
      
                mInflatedViewRef = new WeakReference<View>(view);  
      
                if (mInflateListener != null) {  
                    mInflateListener.onInflate(this, view);  
                }  
      
                return view;  
            } else {  
                throw new IllegalArgumentException("ViewStub must have a valid layoutResource");  
            }  
        } else {  
            throw new IllegalStateException("ViewStub must have a non-null ViewGroup viewParent");  
        }  
    } 