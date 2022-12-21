package com.example.demo.login;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.repository.UserRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private UserDetailsService userService;
	
	//@Autowired
    //private UserDetailsService memberDetailsService;
	
	//パスワードを安全に保管するために、エンコードを行う
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	//securityの設定
	@Override
	public void configure(WebSecurity web) throws Exception {
		//CSSフォルダはsecurity処理を行なわないように設定
	    web.ignoring().antMatchers("/css/**","/js/**");
	}

	//password,userIdの設定
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
    	auth
        .userDetailsService(userService)
        .passwordEncoder(new BCryptPasswordEncoder());
        //.passwordEncoder(passwordEncoder());
    	System.out.println(passwordEncoder().encode("demo1017"));
    	
    	/*auth.inMemoryAuthentication()
    	.withUser("donguri")
        .password(passwordEncoder().encode("5656"))
        .roles("USER");
    	
    	System.out.println(passwordEncoder().encode("5656"));
    	
    	//TODO pass,UserIdはファイルから読み込み
    	//ファイルはAWSに
    	/*String userId = "";
        String pass = "";
        
        try {
        	// ファイルのパスを指定する
        	String path = "C:\\temp\\User.txt";
        	File file = new File(path);
            // ファイルが存在しない場合
            if (!file.exists()) {
                System.out.print("ファイルが存在しません");
                return;
            }
        	FileReader fileReader = new FileReader(file);
        	BufferedReader bufferedReader = new BufferedReader(fileReader);
        	userId = bufferedReader.readLine();
        	
        	// ファイルのパスを指定する
        	path = "C:\\temp\\pass.txt";
        	file = new File(path);
        	// ファイルが存在しない場合
            if (!file.exists()) {
                System.out.print("ファイルが存在しません");
                return;
            }
        	fileReader = new FileReader(file);
        	bufferedReader = new BufferedReader(fileReader);
        	pass = bufferedReader.readLine();
        	
        	bufferedReader.close();
        }catch(IOException e){
        	
        }
        auth.inMemoryAuthentication()
        .withUser(userId)
        .password(passwordEncoder().encode(pass))
        .roles("USER");*/
    	
    }
    
    //URLごとのセキュリティの設定
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	//全てのリクエストの承認は、ログインしていることが条件
    	//authorizeRequests メソッドでアクセス制限の設定を呼び出し
        http.authorizeRequests()
        //authenticated メソッドで認証済み（ログイン済み）のユーザーにのみリクエストを承認する
        	.antMatchers("/signup")
        	.permitAll()
            .anyRequest()
            .authenticated();
        
        //formLogin メソッドでフォーム認証を使用する
        http.formLogin()
        //ログインが必要な場合にユーザーを送信する URL を指定する
            .loginPage("/login")
            .usernameParameter("username") // ログインフォームのユーザー欄のname属性を設定
            .passwordParameter("password") // ログインフォームのパスワード欄のname属性を設定
            //認証後にユーザーがリダイレクトされる場所を指定
            .defaultSuccessUrl("/")
            .permitAll();
        //logout処理の認証
        http.logout()
        .permitAll();
    }
}
