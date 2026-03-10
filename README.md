# 🛸 Rick y Morty APP

![Android](https://img.shields.io/badge/Platform-Android-3DDC84?style=flat-square&logo=android)
![Kotlin](https://img.shields.io/badge/Language-Kotlin-7F52FF?style=flat-square&logo=kotlin)
![Compose](https://img.shields.io/badge/UI-Jetpack_Compose-4285F4?style=flat-square&logo=jetpackcompose)
![Material3](https://img.shields.io/badge/Design-Material_3-757575?style=flat-square)

Aplicación Android moderna desarrollada en **Kotlin** que consume la [API pública de Rick and Morty](https://rickandmortyapi.com/) para visualizar episodios de la serie con una interfaz fluida y organizada por temporadas.

---

## ✨ Características

* **Exploración por Temporadas:** Navegación intuitiva mediante *Horizontal Chips*.
* **Información Detallada:** Lista de episodios con nombre, código técnico y fecha de emisión.
* **Gestión de Estados (LCE):** Manejo robusto de estados de interfaz: **Loading**, **Content** y **Error**.
* **Dark Mode Nativo:** UI optimizada para temas oscuros utilizando componentes de **Material 3**.

---

## 🛠️ Stack Tecnológico

| Capa | Tecnologías |
| :--- | :--- |
| **Lenguaje** | Kotlin |
| **UI Framework** | Jetpack Compose (Material 3) |
| **Arquitectura** | MVVM (ViewModel + StateFlow) |

---

## 📂 Estructura del Proyecto

```
app/src/main/java/cl/sebastianrojo/rickymortyapp/
├── data/           # Modelos DTO y configuración de red (Retrofit)
├── ui/             # Componibles y lógica de navegación
│   └── episodes/   # Pantalla principal y ViewModel
├── state/          # Definición del UiState (Sealed classes)
├── theme/          # Configuración de Material Theme (Color, Type, etc.)
└── MainActivity.kt # Entry point con Navigation Host
```

---

## 🚀 API

* **Base URL:** `https://rickandmortyapi.com/api/`
* **Endpoint:** `GET /episode`
* **Formato:** JSON
