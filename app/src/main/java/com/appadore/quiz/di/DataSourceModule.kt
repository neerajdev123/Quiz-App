package com.appadore.quiz.di

import com.appadore.quiz.data.StaticQuestionStore
import com.appadore.quiz.data.mapper.QuestionResponseMapper
import com.appadore.quiz.model.QuestionResponse
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataSourceModule {

    private const val staticResponse = "{ \"questions\": [ { \"answer_id\": 160, \"countries\": [ { \"country_name\": \"Bosnia and Herzegovina\", \"id\": 29 }, { \"country_name\": \"Mauritania\", \"id\": 142 }, { \"country_name\": \"Chile\", \"id\": 45 }, { \"country_name\": \"New Zealand\", \"id\": 160 } ], \"country_code\": \"NZ\" }, { \"answer_id\": 13, \"countries\": [ { \"country_name\": \"Aruba\", \"id\": 13 }, { \"country_name\": \"Serbia\", \"id\": 184 }, { \"country_name\": \"Montenegro\", \"id\": 150 }, { \"country_name\": \"Moldova\", \"id\": 147 } ], \"country_code\": \"AW\" }, { \"answer_id\": 66, \"countries\": [ { \"country_name\": \"Kenya\", \"id\": 117 }, { \"country_name\": \"Montenegro\", \"id\": 150 }, { \"country_name\": \"Ecuador\", \"id\": 66 }, { \"country_name\": \"Bhutan\", \"id\": 26 } ], \"country_code\": \"EC\" }, { \"answer_id\": 174, \"countries\": [ { \"country_name\": \"Niue\", \"id\": 164 }, { \"country_name\": \"Paraguay\", \"id\": 174 }, { \"country_name\": \"Tuvalu\", \"id\": 232 }, { \"country_name\": \"Indonesia\", \"id\": 105 } ], \"country_code\": \"PY\" }, { \"answer_id\": 122, \"countries\": [ { \"country_name\": \"Kyrgyzstan\", \"id\": 122 }, { \"country_name\": \"Zimbabwe\", \"id\": 250 }, { \"country_name\": \"Saint Lucia\", \"id\": 190 }, { \"country_name\": \"Ireland\", \"id\": 108 } ], \"country_code\": \"KG\" }, { \"answer_id\": 192, \"countries\": [ { \"country_name\": \"Saint Pierre and Miquelon\", \"id\": 192 }, { \"country_name\": \"Namibia\", \"id\": 155 }, { \"country_name\": \"Greece\", \"id\": 87 }, { \"country_name\": \"Anguilla\", \"id\": 8 } ], \"country_code\": \"PM\" }, { \"answer_id\": 113, \"countries\": [ { \"country_name\": \"Belarus\", \"id\": 21 }, { \"country_name\": \"Falkland Islands\", \"id\": 73 }, { \"country_name\": \"Japan\", \"id\": 113 }, { \"country_name\": \"Iraq\", \"id\": 107 } ], \"country_code\": \"JP\" }, { \"answer_id\": 230, \"countries\": [ { \"country_name\": \"Barbados\", \"id\": 20 }, { \"country_name\": \"Italy\", \"id\": 111 }, { \"country_name\": \"Turkmenistan\", \"id\": 230 }, { \"country_name\": \"Cocos Island\", \"id\": 48 } ], \"country_code\": \"TM\" }, { \"answer_id\": 81, \"countries\": [ { \"country_name\": \"Maldives\", \"id\": 137 }, { \"country_name\": \"Aruba\", \"id\": 13 }, { \"country_name\": \"Monaco\", \"id\": 148 }, { \"country_name\": \"Gabon\", \"id\": 81 } ], \"country_code\": \"GA\" }, { \"answer_id\": 141, \"countries\": [ { \"country_name\": \"Martinique\", \"id\": 141 }, { \"country_name\": \"Montenegro\", \"id\": 150 }, { \"country_name\": \"Barbados\", \"id\": 20 }, { \"country_name\": \"Monaco\", \"id\": 148 } ], \"country_code\": \"MQ\" }, { \"answer_id\": 23, \"countries\": [ { \"country_name\": \"Germany\", \"id\": 84 }, { \"country_name\": \"Dominica\", \"id\": 63 }, { \"country_name\": \"Belize\", \"id\": 23 }, { \"country_name\": \"Tuvalu\", \"id\": 232 } ], \"country_code\": \"BZ\" }, { \"answer_id\": 60, \"countries\": [ { \"country_name\": \"Falkland Islands\", \"id\": 73 }, { \"country_name\": \"Czech Republic\", \"id\": 60 }, { \"country_name\": \"Mauritania\", \"id\": 142 }, { \"country_name\": \"British Indian Ocean Territory\", \"id\": 33 } ], \"country_code\": \"CZ\" }, { \"answer_id\": 235, \"countries\": [ { \"country_name\": \"United Arab Emirates\", \"id\": 235 }, { \"country_name\": \"United Arab Emirates\", \"id\": 235 }, { \"country_name\": \"Macedonia\", \"id\": 133 }, { \"country_name\": \"Guernsey\", \"id\": 93 } ], \"country_code\": \"AE\" }, { \"answer_id\": 114, \"countries\": [ { \"country_name\": \"Turks and Caicos Islands\", \"id\": 231 }, { \"country_name\": \"Myanmar\", \"id\": 154 }, { \"country_name\": \"Jersey\", \"id\": 114 }, { \"country_name\": \"Ethiopia\", \"id\": 72 } ], \"country_code\": \"JE\" }, { \"answer_id\": 126, \"countries\": [ { \"country_name\": \"Malawi\", \"id\": 135 }, { \"country_name\": \"Trinidad and Tobago\", \"id\": 227 }, { \"country_name\": \"Nepal\", \"id\": 157 }, { \"country_name\": \"Lesotho\", \"id\": 126 } ], \"country_code\": \"LS\" } ] }"

    @Provides
    @Singleton
    fun provideStaticDataSource(): StaticQuestionStore {
        return StaticQuestionStore(provideStaticResponse(), QuestionResponseMapper())
    }

    @Provides
    @Singleton
    fun provideStaticResponse() : QuestionResponse {
        return Gson().fromJson(staticResponse, QuestionResponse::class.java)
    }

}