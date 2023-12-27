package com.example.dentall;

import com.example.dentall.beans.Student;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @GET("student")
    Call<List<Student>> getStudents();

}

