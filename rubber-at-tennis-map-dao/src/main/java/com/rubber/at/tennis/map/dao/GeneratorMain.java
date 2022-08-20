package com.rubber.at.tennis.map.dao;
import com.rubber.base.components.mysql.utils.MybatisBaseCodeGenerator;

/**
 * @author luffyu
 * Created on 2022/5/14
 */
public class GeneratorMain extends MybatisBaseCodeGenerator {

    public GeneratorMain(CodeGeneratorConfigBean codeGeneratorConfigBean) {
        super(codeGeneratorConfigBean);
    }

    public static void main(String[] args) {
        CodeGeneratorConfigBean configBean = new CodeGeneratorConfigBean();
        configBean.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/at_tennis?useUnicode=true;characterEncoding=utf-8");
        configBean.setDriverName("com.mysql.cj.jdbc.Driver");
        configBean.setUserName("root");
        configBean.setPassword("root");
        GeneratorMain codeGenerator = new GeneratorMain(configBean);
        codeGenerator.create("t_tennis_court_map");
    }

}
