package com.fisek.ws.shared;

public record GenelMesaj(String message) { // 'GenelMesaj' sınıfı, sadece bir 'message' alanı taşıyan ve otomatik olarak 
// 'toString()', 'equals()', ve 'hashCode()' metodlarını sağlayan immutable bir veri taşıyıcıdır.
    
}
