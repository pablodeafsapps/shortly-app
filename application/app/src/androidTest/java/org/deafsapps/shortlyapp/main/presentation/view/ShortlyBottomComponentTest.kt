package org.deafsapps.shortlyapp.main.presentation.view

import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.deafsapps.shortlyapp.R
import org.junit.Rule
import org.junit.Test

class ShortlyBottomComponentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var candidateText: String

    @Test
    fun when_ShortlyBottomComponent_load_and_hasInputError_is_false__a_placeholder_is_displayed() {
        // given
        composeTestRule.setContent {
            candidateText = stringResource(id = R.string.shorten_a_link_here)
            ShortlyBottomComponent(
                hasInputError = false,
                onShortenUrlSelected = {}
            )
        }
        // when
        composeTestRule
            .onNodeWithTag(SHORTLY_BOTTOM_COMPONENT_URL_TEXT_FIELD_TAG)
            .assertTextContains(candidateText)
    }

    @Test
    fun when_ShortlyBottomComponent_load_and_hasInputError_is_true__an_error_message_is_displayed() {
        // given
        composeTestRule.setContent {
            candidateText = stringResource(id = R.string.please_add_a_link_here)
            ShortlyBottomComponent(
                hasInputError = true,
                onShortenUrlSelected = {}
            )
        }
        // when
        composeTestRule
            .onNodeWithTag(SHORTLY_BOTTOM_COMPONENT_URL_TEXT_FIELD_TAG)
            .assertTextContains(candidateText)
    }

}
