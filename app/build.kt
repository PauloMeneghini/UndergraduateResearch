// Adicione dentro do bloco dependencies { ... }

// Retrofit para chamadas de rede
implementation("com.squareup.retrofit2:retrofit:2.9.0")
implementation("com.squareup.retrofit2:converter-gson:2.9.0") // Conversor para JSON com Gson

// Coroutines para operações assíncronas
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.0") // ViewModel com suporte a coroutines
implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.0") // lifecycleScope

// Opcional, mas recomendado: Coil para carregar imagens da URL
implementation("io.coil-kt:coil:2.5.0")
