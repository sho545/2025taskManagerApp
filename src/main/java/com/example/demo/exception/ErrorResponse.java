package com.example.demo.exception;

import java.time.LocalDateTime;

//private final LocalDateTime timestamp;~
//timestamp~をすべて受け取るコンストラクタ
//timestamp()~のアクセッサ
//equels()などのメソッド自動生成
//{}は省略可
public record ErrorResponse(
    LocalDateTime timestamp,
    int status,
    String error,
    String message,
    String path) {
}
