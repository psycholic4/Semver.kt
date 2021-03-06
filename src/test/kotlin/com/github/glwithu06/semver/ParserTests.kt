package com.github.glwithu06.semver

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ParserTests {
    @Test
    fun testParseBasicVersion() {
        val ver = Semver("1.452.368")

        assertEquals(ver.major, "1")
        assertEquals(ver.minor, "452")
        assertEquals(ver.patch, "368")
        assertEquals(ver.prereleaseIdentifiers, emptyList())
        assertEquals(ver.buildMetadataIdentifiers, emptyList())
    }
    
    @Test
    fun testParsePrereleaseVersion() {
        val ver = Semver("1.452.368-rc.alpha.11.log-test")
        assertEquals(ver.major, "1")
        assertEquals(ver.minor, "452")
        assertEquals(ver.patch, "368")
        assertEquals(ver.prereleaseIdentifiers.count(), 4)
        assertEquals(ver.prereleaseIdentifiers[0], "rc")
        assertEquals(ver.prereleaseIdentifiers[1], "alpha")
        assertEquals(ver.prereleaseIdentifiers[2], "11")
        assertEquals(ver.prereleaseIdentifiers[3], "log-test")
        assertEquals(ver.buildMetadataIdentifiers, emptyList())
    }

    @Test
    fun testParseBuildMetadataVersion() {
        val ver = Semver("1.452.368+sha.exp.5114f85.20190121")

        assertEquals(ver.major, "1")
        assertEquals(ver.minor, "452")
        assertEquals(ver.patch, "368")
        assertEquals(ver.prereleaseIdentifiers, emptyList())
        assertEquals(ver.buildMetadataIdentifiers.count(), 4)
        assertEquals(ver.buildMetadataIdentifiers[0], "sha")
        assertEquals(ver.buildMetadataIdentifiers[1], "exp")
        assertEquals(ver.buildMetadataIdentifiers[2], "5114f85")
        assertEquals(ver.buildMetadataIdentifiers[3], "20190121")
    }

    @Test
    fun testParseBigNumberVersion() {
        val ver = Semver("69938113471411635120691317071569414.64537206108257636612034178144141277.47527207420859796686256474452275428")
        assertEquals(ver.major, "69938113471411635120691317071569414")
        assertEquals(ver.minor, "64537206108257636612034178144141277")
        assertEquals(ver.patch, "47527207420859796686256474452275428")
    }

    @Test
    fun testParseFullVersion() {
        val ver = Semver("69938113471411635120691317071569414.452.368-rc.alpha.11.log-test+sha.exp.5114f85.20190121")

        assertEquals(ver.major, "69938113471411635120691317071569414")
        assertEquals(ver.minor, "452")
        assertEquals(ver.patch, "368")
        assertEquals(ver.prereleaseIdentifiers.count(), 4)
        assertEquals(ver.prereleaseIdentifiers[0], "rc")
        assertEquals(ver.prereleaseIdentifiers[1], "alpha")
        assertEquals(ver.prereleaseIdentifiers[2], "11")
        assertEquals(ver.prereleaseIdentifiers[3], "log-test")
        assertEquals(ver.buildMetadataIdentifiers.count(), 4)
        assertEquals(ver.buildMetadataIdentifiers[0], "sha")
        assertEquals(ver.buildMetadataIdentifiers[1], "exp")
        assertEquals(ver.buildMetadataIdentifiers[2], "5114f85")
        assertEquals(ver.buildMetadataIdentifiers[3], "20190121")
    }

    @Test
    fun testParsePrefixedVersion() {
        val ver = Semver("v001.452.368-rc.alpha.11.log-test")

        assertEquals(ver.major, "001")
        assertEquals(ver.minor, "452")
        assertEquals(ver.patch, "368")
        assertEquals(ver.prereleaseIdentifiers.count(), 4)
        assertEquals(ver.prereleaseIdentifiers[0], "rc")
        assertEquals(ver.prereleaseIdentifiers[1], "alpha")
        assertEquals(ver.prereleaseIdentifiers[2], "11")
        assertEquals(ver.prereleaseIdentifiers[3], "log-test")
        assertEquals(ver.buildMetadataIdentifiers, emptyList())
    }

    @Test
    fun testParseMajorOnlyVersion() {
        val ver = Semver("v1-rc.alpha.11.log-test")

        assertEquals(ver.major, "1")
        assertEquals(ver.minor, "0")
        assertEquals(ver.patch, "0")
        assertEquals(ver.prereleaseIdentifiers.count(), 4)
        assertEquals(ver.prereleaseIdentifiers[0], "rc")
        assertEquals(ver.prereleaseIdentifiers[1], "alpha")
        assertEquals(ver.prereleaseIdentifiers[2], "11")
        assertEquals(ver.prereleaseIdentifiers[3], "log-test")
        assertEquals(ver.buildMetadataIdentifiers, emptyList())
    }

    @Test
    fun testParseMajorMinorVersion() {
        val ver = Semver("v1.354-rc.alpha.11.log-test")

        assertEquals(ver.major, "1")
        assertEquals(ver.minor, "354")
        assertEquals(ver.patch, "0")
        assertEquals(ver.prereleaseIdentifiers.count(), 4)
        assertEquals(ver.prereleaseIdentifiers[0], "rc")
        assertEquals(ver.prereleaseIdentifiers[1], "alpha")
        assertEquals(ver.prereleaseIdentifiers[2], "11")
        assertEquals(ver.prereleaseIdentifiers[3], "log-test")
        assertEquals(ver.buildMetadataIdentifiers, emptyList())
    }

    @Test
    fun testParseInvalidVersion() {
        val invalidVersions = listOf(
            "",
            "lorem ipsum",
            "0.a.0-pre+meta",
            "0.0.b-pre+meta",
            "0.0.0- +meta",
            "0.0.0-+meta",
            "0.0.0-+",
            "0.0.0-_+meta",
            "0.0.0-pre+_",
            "0.-100.3"
        )
        for (version in invalidVersions) {
            assertFailsWith<IllegalArgumentException> {
                print(Semver(version).toString())
            }
        }
    }

    @Test
    fun testParseIntVersion() {
        val ver = Semver(1)

        assertEquals(ver.major, "1")
        assertEquals(ver.minor, "0")
        assertEquals(ver.patch, "0")
        assertEquals(ver.prereleaseIdentifiers, emptyList())
        assertEquals(ver.buildMetadataIdentifiers, emptyList())
    }

    @Test
    fun testParseNegativeIntVersion() {
        assertFailsWith<IllegalArgumentException> {
            print(Semver(-11).toString())
        }
    }

    @Test
    fun testParseFloatVersion() {
        val ver = Semver(1.5637881234)

        assertEquals(ver.major, "1")
        assertEquals(ver.minor, "5637881234")
        assertEquals(ver.patch, "0")
        assertEquals(ver.prereleaseIdentifiers, emptyList())
        assertEquals(ver.buildMetadataIdentifiers, emptyList())
    }

    @Test
    fun testParseStringVersion() {
        val ver = Semver("v001.452.368-rc.alpha.11.log-test")

        assertEquals(ver.major, "001")
        assertEquals(ver.minor, "452")
        assertEquals(ver.patch, "368")
        assertEquals(ver.prereleaseIdentifiers.count(), 4)
        assertEquals(ver.prereleaseIdentifiers[0], "rc")
        assertEquals(ver.prereleaseIdentifiers[1], "alpha")
        assertEquals(ver.prereleaseIdentifiers[2], "11")
        assertEquals(ver.prereleaseIdentifiers[3], "log-test")
        assertEquals(ver.buildMetadataIdentifiers, emptyList())
    }

    @Test
    fun testParseInvalidStringVersion() {
        val invalidVersions = listOf(
            "",
            "lorem ipsum",
            "0.a.0-pre+meta",
            "0.0.b-pre+meta",
            "0.0.0- +meta",
            "0.0.0-+meta",
            "0.0.0-+",
            "0.0.0-_+meta",
            "0.0.0-pre+_",
            "0.-100.3"
        )
        for (version in invalidVersions) {
            assertFailsWith<IllegalArgumentException> {
                print(Semver(version).toString())
            }
        }
    }

    @Test
    fun testIntegerToVersion() {
        val ver = 10.toVersion()

        assertEquals(ver.major, "10")
        assertEquals(ver.minor, "0")
        assertEquals(ver.patch, "0")
        assertEquals(ver.prereleaseIdentifiers, emptyList())
        assertEquals(ver.buildMetadataIdentifiers, emptyList())
    }

    @Test
    fun testIntegerToVersionOrNull() {
        val ver = 10.toVersionOrNull()

        assertEquals(ver?.major, "10")
        assertEquals(ver?.minor, "0")
        assertEquals(ver?.patch, "0")
        assertEquals(ver?.prereleaseIdentifiers, emptyList())
        assertEquals(ver?.buildMetadataIdentifiers, emptyList())
    }

    @Test
    fun testInvalidIntegerToVersion() {
        assertEquals((-10).toVersionOrNull(), null)
        assertFailsWith<IllegalArgumentException> {
            (-10).toVersion()
        }
    }

    @Test
    fun testFloatToVersion() {
        val ver = 10.346593.toVersion()

        assertEquals(ver.major, "10")
        assertEquals(ver.minor, "346593")
        assertEquals(ver.patch, "0")
        assertEquals(ver.prereleaseIdentifiers, emptyList())
        assertEquals(ver.buildMetadataIdentifiers, emptyList())
    }

    @Test
    fun testFloatToVersionOrNull() {
        val ver = 10.346593.toVersionOrNull()

        assertEquals(ver?.major, "10")
        assertEquals(ver?.minor, "346593")
        assertEquals(ver?.patch, "0")
        assertEquals(ver?.prereleaseIdentifiers, emptyList())
        assertEquals(ver?.buildMetadataIdentifiers, emptyList())
    }

    @Test
    fun testInvalidFloatToVersion() {
        assertEquals((-10.346593).toVersionOrNull(), null)
        assertFailsWith<IllegalArgumentException> {
            (-10.346593).toVersion()
        }
    }

    @Test
    fun testStringToVersion() {
        val ver = "69938113471411635120691317071569414.452.368-rc.alpha.11.log-test+sha.exp.5114f85.20190121".toVersion()

        assertEquals(ver.major, "69938113471411635120691317071569414")
        assertEquals(ver.minor, "452")
        assertEquals(ver.patch, "368")
        assertEquals(ver.prereleaseIdentifiers.count(), 4)
        assertEquals(ver.prereleaseIdentifiers[0], "rc")
        assertEquals(ver.prereleaseIdentifiers[1], "alpha")
        assertEquals(ver.prereleaseIdentifiers[2], "11")
        assertEquals(ver.prereleaseIdentifiers[3], "log-test")
        assertEquals(ver.buildMetadataIdentifiers.count(), 4)
        assertEquals(ver.buildMetadataIdentifiers[0], "sha")
        assertEquals(ver.buildMetadataIdentifiers[1], "exp")
        assertEquals(ver.buildMetadataIdentifiers[2], "5114f85")
        assertEquals(ver.buildMetadataIdentifiers[3], "20190121")
    }

    @Test
    fun testStringToVersionOrNull() {
        val ver = "69938113471411635120691317071569414.452.368-rc.alpha.11.log-test+sha.exp.5114f85.20190121".toVersionOrNull()

        assertEquals(ver?.major, "69938113471411635120691317071569414")
        assertEquals(ver?.minor, "452")
        assertEquals(ver?.patch, "368")
        assertEquals(ver?.prereleaseIdentifiers?.count(), 4)
        assertEquals(ver?.prereleaseIdentifiers?.get(0), "rc")
        assertEquals(ver?.prereleaseIdentifiers?.get(1), "alpha")
        assertEquals(ver?.prereleaseIdentifiers?.get(2), "11")
        assertEquals(ver?.prereleaseIdentifiers?.get(3), "log-test")
        assertEquals(ver?.buildMetadataIdentifiers?.count(), 4)
        assertEquals(ver?.buildMetadataIdentifiers?.get(0), "sha")
        assertEquals(ver?.buildMetadataIdentifiers?.get(1), "exp")
        assertEquals(ver?.buildMetadataIdentifiers?.get(2), "5114f85")
        assertEquals(ver?.buildMetadataIdentifiers?.get(3), "20190121")
    }

    @Test
    fun testInvalidStringToVersion() {
        assertEquals("0.a.0-pre+meta".toVersionOrNull(), null)
        assertFailsWith<IllegalArgumentException> {
            "0.a.0-pre+meta".toVersion()
        }
    }
}