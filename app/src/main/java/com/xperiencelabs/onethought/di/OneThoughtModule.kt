package com.xperiencelabs.onethought.di

import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.xperiencelabs.onethought.data.AuthenticationImpl
import com.xperiencelabs.onethought.data.PostRepositoryImpl
import com.xperiencelabs.onethought.data.UserRepositoryImpl
import com.xperiencelabs.onethought.domain.repository.authentication.Authentication
import com.xperiencelabs.onethought.domain.repository.post.PostRepository
import com.xperiencelabs.onethought.domain.repository.user.UserRepository
import com.xperiencelabs.onethought.domain.use_cases.authentication.*
import com.xperiencelabs.onethought.domain.use_cases.postUseCases.GetAllPosts
import com.xperiencelabs.onethought.domain.use_cases.postUseCases.PostUseCases
import com.xperiencelabs.onethought.domain.use_cases.postUseCases.UploadImage
import com.xperiencelabs.onethought.domain.use_cases.postUseCases.UploadPost
import com.xperiencelabs.onethought.domain.use_cases.userUseCases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OneThoughtModule{

    @Provides
    @Singleton
    fun provideFirebaseAuth():FirebaseAuth{
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideFireStore():FirebaseFirestore{
          return  FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideFirebaseStorage():FirebaseStorage{
        return FirebaseStorage.getInstance()
    }


    //---------------------------Authentication---------------------------------//
    @Provides
    @Singleton
    fun provideAuthenticationRepo(auth: FirebaseAuth,fireStore: FirebaseFirestore):Authentication{
        return AuthenticationImpl(auth,fireStore)
    }
    @Provides
    @Singleton
    fun provideAuthentication(repository:Authentication) = Authentication(

        UserAuthenticationState = UserAuthenticationState(repository),
        FirebaseAuthState = FirebaseAuthState(repository),
        SignIn = SignIn(repository),
        SignOut = SignOut(repository),
        SignUp = SignUp(repository)
    )
    //---------------------------UserRepository---------------------------------//
    @Provides
    @Singleton
    fun provideUserRepo(fireStore: FirebaseFirestore):UserRepository{
        return UserRepositoryImpl(fireStore)
    }

    @Provides
    @Singleton
    fun provideUserUseCases(userRepository: UserRepository) =UserUseCases(
        EditAbout(userRepository),
        EditProfile(userRepository),
        EditName(userRepository),
        GetUserDetails(userRepository)
    )
    //---------------------------Posts---------------------------------//
    @Provides
    @Singleton
    fun provideFireStoreCollection(firebaseFirestore:FirebaseFirestore): CollectionReference {
        return firebaseFirestore.collection("Posts")
    }

    @Provides
    @Singleton
    fun providePostRepository(firestoreCollection: CollectionReference,storage: FirebaseStorage):PostRepository{
        return PostRepositoryImpl(firestoreCollection,storage)
    }

    @Provides
    @Singleton
    fun providePostUseCase(postRepository: PostRepository)=PostUseCases(
        GetAllPosts(postRepository),
        UploadPost(postRepository),
        UploadImage(postRepository)
    )


}