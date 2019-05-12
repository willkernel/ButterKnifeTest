package com.willkernel.compile;

import com.google.auto.service.AutoService;
import com.willkernel.annotation.BindView;
import com.willkernel.annotation.OnClick;
import com.willkernel.annotation.OnLongClick;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.JavaFileObject;

/**
 * getEnclosedElements()返回该元素直接包含的子元素
 * getEnclosingElement()返回包含该element的父element，与上一个方法相反
 * getKind()返回element的类型，判断是哪种element
 * getModifiers()获取修饰关键字,入public static final等关键字
 * getSimpleName()获取名字，不带包名
 * getQualifiedName()获取全名，如果是类的话，包含完整的包名路径
 * getParameters()用于获取方法的参数元素，每个元素是一个VariableElement
 * getReturnType()获取方法元素的返回值
 * getConstantValue()如果属性变量被final修饰，则可以使用该方法获取它的值
 */
@AutoService(Processor.class)//触发注解处理器
public class ButterKnifeProcess extends AbstractProcessor {
    private Messager messager;// 报告错误,警告,其他提示信息
    private Elements elementsUtils;//操作element 工具方法
    private Filer filer;// 创建新的源文件,class文件以及辅助文件
    private Types typesUtils;//types中包含用于操作typemirror的工具方法

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        elementsUtils = processingEnvironment.getElementUtils();
        filer = processingEnvironment.getFiler();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        // 获取支持BindView注解的类型
        types.add(BindView.class.getCanonicalName());
        types.add(OnClick.class.getCanonicalName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        // android.util.Log.i(TAG, "start ->");
        System.out.println("开始 ----------------------------------->");

        System.out.println("所有带BindView注解的属性 ----------------------------------->");
        // 获取MainActivity中所有带BindView注解的属性
        Set<? extends Element> bindViewSet = roundEnvironment.getElementsAnnotatedWith(BindView.class);
        // 保存键值对，key是com.netease.butterknife.MainActivity   value是所有带BindView注解的属性集合
        Map<String, List<VariableElement>> bindViewMap = new HashMap<>();
        // 遍历所有带BindView注解的属性
        for (Element element : bindViewSet) {
            // 转成原始属性元素（结构体元素）
            VariableElement variableElement = (VariableElement) element;
            // 通过属性元素获取它所属的MainActivity类名，如：com.netease.butterknife.MainActivity
            String activityName = getActivityName(variableElement);
            // 从缓存集合中获取MainActivity所有带BindView注解的属性集合
            List<VariableElement> list = bindViewMap.get(activityName);
            if (list == null) {
                list = new ArrayList<>();
                // 先加入map集合，引用变量list可以动态改变值
                bindViewMap.put(activityName, list);
            }
            // 将MainActivity所有带BindView注解的属性加入到list集合
            list.add(variableElement);
            // 测试打印：每个属性的名字
            System.out.println("variableElement >>> " + variableElement.getSimpleName().toString());
        }

        System.out.println("所有带OnClick注解的方法 ----------------------------------->");
        // 获取MainActivity中所有带OnClick注解的方法
        Set<? extends Element> onClickSet = roundEnvironment.getElementsAnnotatedWith(OnClick.class);
        // 保存键值对，key是com.netease.butterknife.MainActivity   value是所有带OnClick注解的方法集合
        Map<String, List<ExecutableElement>> onClickMap = new HashMap<>();
        // 遍历所有带OnClick注解的方法
        for (Element element : onClickSet) {
            // 转成原始属性元素（结构体元素）
            ExecutableElement executableElement = (ExecutableElement) element;
            // 通过属性元素获取它所属的MainActivity类名，如：com.netease.butterknife.MainActivity
            String activityName = getActivityName(executableElement);
            // 从缓存集合中获取MainActivity所有带OnClick注解的方法集合
            List<ExecutableElement> list = onClickMap.get(activityName);
            if (list == null) {
                list = new ArrayList<>();
                // 先加入map集合，引用变量list可以动态改变值
                onClickMap.put(activityName, list);
            }
            // 将MainActivity所有带OnClick注解的方法加入到list集合
            list.add(executableElement);
            // 测试打印：每个方法的名字
            System.out.println("executableElement >>> " + executableElement.getSimpleName().toString());
        }

        System.out.println("所有带OnLongClick注解的方法 ----------------------------------->");
        // 获取MainActivity中所有带OnLongClick注解的方法
        Set<? extends Element> onLongClickSet = roundEnvironment.getElementsAnnotatedWith(OnLongClick.class);
        // 保存键值对，key是com.netease.butterknife.MainActivity   value是所有带OnLongClick注解的方法集合
        Map<String, List<ExecutableElement>> onLongClickMap = new HashMap<>();
        // 遍历所有带OnLongClick注解的方法
        for (Element element : onLongClickSet) {
            // 转成原始属性元素（结构体元素）
            ExecutableElement executableElement = (ExecutableElement) element;
            // 通过属性元素获取它所属的MainActivity类名，如：com.netease.butterknife.MainActivity
            String activityName = getActivityName(executableElement);
            // 从缓存集合中获取MainActivity所有带OnLongClick注解的方法集合
            List<ExecutableElement> list = onLongClickMap.get(activityName);
            if (list == null) {
                list = new ArrayList<>();
                // 先加入map集合，引用变量list可以动态改变值
                onLongClickMap.put(activityName, list);
            }
            // 将MainActivity所有带OnLongClick注解的方法加入到list集合
            list.add(executableElement);
            // 测试打印：每个方法的名字
            System.out.println("executableElement >>> " + executableElement.getSimpleName().toString());
        }

        //----------------------------------造币过程------------------------------------
        // 获取Activity完整的字符串类名（包名 + 类名）
        for (String activityName : bindViewMap.keySet()) {
            // 获取"com.netease.butterknife.MainActivity"中所有控件属性的集合
            List<VariableElement> cacheElements = bindViewMap.get(activityName);
            List<ExecutableElement> clickElements = onClickMap.get(activityName);
            List<ExecutableElement> longClickElements = onLongClickMap.get(activityName);

            Filer filer = processingEnv.getFiler();
            try {
                // 创建一个新的源文件（Class），并返回一个对象以允许写入它
                JavaFileObject javaFileObject = filer.createSourceFile(activityName + "$ViewBinder");
                // 通过属性标签获取包名标签（任意一个属性标签的父节点都是同一个包名）
                String packageName = getPackageName(cacheElements.get(0));
                // 定义Writer对象，开启造币过程
                Writer writer = javaFileObject.openWriter();

                // 类名：MainActivity$ViewBinder，不是com.netease.butterknife.MainActivity$ViewBinder
                // 通过属性元素获取它所属的MainActivity类名，再拼接后结果为：MainActivity$ViewBinder
                String activitySimpleName = cacheElements.get(0).getEnclosingElement()
                        .getSimpleName().toString() + "$ViewBinder";

                System.out.println("activityName >>> " + activityName + "\nactivitySimpleName >>> " + activitySimpleName);

                System.out.println("开始造币 ----------------------------------->");
                // 第一行生成包
                writer.write("package " + packageName + ";\n");
                // 第二行生成要导入的接口类（必须手动导入）
                writer.write("import com.willkernel.butterknifelibrary.ViewBinder;\n");
                writer.write("import com.willkernel.butterknifelibrary.DebouncingOnClickListener;\n");
                writer.write("import android.view.View;\n");
//                writer.write("import android.util.Log;\n");
//                writer.write("// 彭老师到此一游！\n");

                // 第三行生成类
                writer.write("public class " + activitySimpleName +
                        " implements ViewBinder<" + activityName + "> {\n");
                // 第四行生成bind方法
                writer.write("public void bind(final " + activityName + " target) {\n");

                System.out.println("每个控件属性 ----------------------------------->");
                // 循环生成MainActivity每个控件属性
                for (VariableElement variableElement : cacheElements) {
                    // 控件属性名
                    String fieldName = variableElement.getSimpleName().toString();
                    // 获取控件的注解
                    BindView bindView = variableElement.getAnnotation(BindView.class);
                    // 获取控件注解的id值
                    int id = bindView.value();
                    // 生成：target.tv = target.findViewById(xxx);
                    writer.write("target." + fieldName + " = " + "target.findViewById(" + id + ");\n");
                }

                // 恐怖的开始……
//                writer.write("for (int i = 0; i < 100; i++) {\n");
//                writer.write("System.out.println(i);\n}\n");

                System.out.println("每个点击事件 ----------------------------------->");
                if (clickElements != null && !clickElements.isEmpty()) {
                    // 循环生成MainActivity每个点击事件
                    for (ExecutableElement executableElement : clickElements) {
                        // 获取方法名
                        String methodName = executableElement.getSimpleName().toString();
                        // 获取方法的注解
                        OnClick onClick = executableElement.getAnnotation(OnClick.class);
                        // 获取方法注解的id值
                        int[] ids = onClick.value();
                        // 获取方法参数
                        List<? extends VariableElement> parameters = executableElement.getParameters();

                        // 生成点击事件
                        for (int id : ids) {
                            writer.write("target.findViewById(" + id + ").setOnClickListener(new DebouncingOnClickListener() {\n");
                            writer.write("public void doClick(View view) {\n");
                            if (parameters.isEmpty()) {
                                writer.write("target." + methodName + "();\n}\n});\n");
                            } else {
                                writer.write("target." + methodName + "(view);\n}\n});\n");
                            }
                        }
                    }
                }

                System.out.println("每个长按事件 ----------------------------------->");
                if (longClickElements != null && !longClickElements.isEmpty()) {
                    // 循环生成MainActivity每个长按事件
                    for (ExecutableElement executableElement : longClickElements) {
                        // 获取方法名
                        String methodName = executableElement.getSimpleName().toString();
                        // 获取方法的注解
                        OnLongClick onLongClick = executableElement.getAnnotation(OnLongClick.class);
                        // 获取方法注解的id值
                        int[] ids = onLongClick.value();
                        // 获取方法参数
                        List<? extends VariableElement> parameters = executableElement.getParameters();

                        for (int id : ids) {
                            // 生成长按事件
                            writer.write("target.findViewById(" + id + ").setOnLongClickListener(new View.OnLongClickListener() {\n");
                            writer.write("public boolean onLongClick(View view) {\n");
                            if (parameters.isEmpty()) {
                                writer.write("target." + methodName + "();\n");
                                writer.write("return true;\n}\n});\n");
                            } else {
                                writer.write("target." + methodName + "(view);\n");
                                writer.write("return true;\n}\n});\n");
                            }
                        }
                    }
                }

                // 恐怖继续中……
//                writer.write("\nsimon();\n}\n");
//                writer.write("public void simon() {Log.e(\"NetEase >>>\", \"hello simon\");\n");

                writer.write("}\npublic void unBind(" + activityName + " target) {\n");
                writer.write("if (target != null) {\n");
                // 循环每个控件属性
                for (VariableElement variableElement : cacheElements) {
                    // 控件属性名
                    String fieldName = variableElement.getSimpleName().toString();
                    writer.write("target." + fieldName + " = null;\n");
                }
                writer.write("}");

                // 最后结束标签，造币完成
                writer.write("\n}\n}");
                System.out.println("结束 ----------------------------------->");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 通过属性标签获取类名标签，再通过类名标签获取包名标签
     *
     * @param variableElement 属性标签
     * @return com.netease.butterknife.MainActivity（包名 + 类名）
     */
    private String getActivityName(VariableElement variableElement) {
        // 通过属性标签获取类名标签，再通过类名标签获取包名标签
        String packageName = getPackageName(variableElement);
        // 通过属性标签获取类名标签
        TypeElement typeElement = (TypeElement) variableElement.getEnclosingElement();
        // 完整字符串拼接：com.netease.butterknife + "." + MainActivity
        return packageName + "." + typeElement.getSimpleName().toString();
    }

    private String getActivityName(ExecutableElement executableElement) {
        // 通过方法标签获取类名标签，再通过类名标签获取包名标签
        String packageName = getPackageName(executableElement);
        // 通过方法标签获取类名标签
        TypeElement typeElement = (TypeElement) executableElement.getEnclosingElement();
        // 完整字符串拼接：com.netease.butterknife + "." + MainActivity
        return packageName + "." + typeElement.getSimpleName().toString();
    }

    // 通过属性标签获取类名标签，再通过类名标签获取包名标签（通过属性节点，找到父节点、再找到父节点的父节点）
    private String getPackageName(VariableElement variableElement) {
        // 通过属性标签获取类名标签
        TypeElement typeElement = (TypeElement) variableElement.getEnclosingElement();
        // 通过类名标签获取包名标签
        String packageName = processingEnv.getElementUtils().getPackageOf(typeElement).getQualifiedName().toString();
        System.out.println("packageName >>>  " + packageName);
        return packageName;
    }

    private String getPackageName(ExecutableElement executableElement) {
        // 通过方法标签获取类名标签
        TypeElement typeElement = (TypeElement) executableElement.getEnclosingElement();
        // 通过类名标签获取包名标签
        String packageName = processingEnv.getElementUtils().getPackageOf(typeElement).getQualifiedName().toString();
        System.out.println("packageName >>>  " + packageName);
        return packageName;
    }
}