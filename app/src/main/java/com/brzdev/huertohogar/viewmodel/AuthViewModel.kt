package com.brzdev.huertohogar.viewmodel

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.brzdev.huertohogar.data.AppDatabase
import com.brzdev.huertohogar.data.SessionManager
import com.brzdev.huertohogar.data.UserDao
import com.brzdev.huertohogar.modelo.User // <-- USANDO TU CARPETA 'modelo'
import com.brzdev.huertohogar.utils.PasswordHasher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Define los posibles estados de autenticación
enum class AuthState {
    LOADING,
    LOGGED_IN,
    LOGGED_OUT
}

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val userDao: UserDao
    private val sessionManager: SessionManager

    // Estado de autenticación (Cargando, Dentro, Fuera)
    private val _authState = MutableStateFlow(AuthState.LOADING)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    // Perfil del usuario conectado
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    init {
        // Obtenemos las instancias de la DB y del Gestor de Sesión
        val db = AppDatabase.getDatabase(application)
        userDao = db.userDao()
        sessionManager = SessionManager(application)

        // Comprueba si ya hay una sesión activa al iniciar la app
        checkCurrentUser()
    }

    private fun checkCurrentUser() {
        viewModelScope.launch {
            _authState.value = AuthState.LOADING
            val userId = sessionManager.getUserId()
            if (userId != -1) {
                // Si hay un ID guardado, busca el usuario en la DB
                val user = userDao.getUserById(userId)
                if (user != null) {
                    _currentUser.value = user
                    _authState.value = AuthState.LOGGED_IN
                } else {
                    // El usuario no existe (raro), borra la sesión
                    sessionManager.clearSession()
                    _authState.value = AuthState.LOGGED_OUT
                }
            } else {
                _authState.value = AuthState.LOGGED_OUT
            }
        }
    }

    fun signUp(email: String, password: String, context: Context) {
        viewModelScope.launch {
            _authState.value = AuthState.LOADING
            // 1. Revisa si el email ya existe
            if (userDao.getUserByEmail(email) != null) {
                Toast.makeText(context, "El email ya está registrado", Toast.LENGTH_SHORT).show()
                _authState.value = AuthState.LOGGED_OUT
                return@launch
            }

            // 2. Hashea la contraseña
            val hashedPassword = PasswordHasher.hash(password)

            // 3. Crea el usuario
            val newUser = User(email = email, hashedPassword = hashedPassword)

            // 4. Inserta el usuario
            userDao.insertUser(newUser)

            // 5. Inicia sesión (simulado, obteniendo el usuario recién creado)
            val registeredUser = userDao.getUserByEmail(email)!!
            sessionManager.saveSession(registeredUser.uid)
            _currentUser.value = registeredUser
            _authState.value = AuthState.LOGGED_IN
            Toast.makeText(context, "Registro exitoso", Toast.LENGTH_SHORT).show()
        }
    }

    fun signIn(email: String, password: String, context: Context) {
        viewModelScope.launch {
            _authState.value = AuthState.LOADING
            // 1. Busca al usuario por email
            val user = userDao.getUserByEmail(email)
            if (user == null) {
                Toast.makeText(context, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
                _authState.value = AuthState.LOGGED_OUT
                return@launch
            }

            // 2. Hashea la contraseña ingresada y compárala
            val hashedPassword = PasswordHasher.hash(password)
            if (user.hashedPassword != hashedPassword) {
                Toast.makeText(context, "Contraseña incorrecta", Toast.LENGTH_SHORT).show()
                _authState.value = AuthState.LOGGED_OUT
                return@launch
            }

            // 3. Inicia sesión
            sessionManager.saveSession(user.uid)
            _currentUser.value = user
            _authState.value = AuthState.LOGGED_IN
            Toast.makeText(context, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
        }
    }

    fun signOut() {
        viewModelScope.launch {
            sessionManager.clearSession()
            _currentUser.value = null
            _authState.value = AuthState.LOGGED_OUT
        }
    }

    // --- ESTA FUNCIÓN REEMPLAZA AL ProfileViewModel ---
    fun saveUserProfile(address: String, phone: String, context: Context) {
        viewModelScope.launch {
            val user = _currentUser.value
            if (user != null) {
                // Crea una copia del usuario actual con los datos nuevos
                val updatedUser = user.copy(address = address, phone = phone)
                userDao.updateUser(updatedUser)
                _currentUser.value = updatedUser // Actualiza el estado local
                Toast.makeText(context, "Perfil guardado", Toast.LENGTH_SHORT).show()
            }
        }
    }
}