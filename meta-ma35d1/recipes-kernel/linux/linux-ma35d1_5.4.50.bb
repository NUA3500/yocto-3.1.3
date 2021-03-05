# Copyright (C) 2019-2020
# Copyright 2019-2020 Nuvoton
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "Linux Kernel provided and supported by Nuvoton"
DESCRIPTION = "Linux Kernel provided and supported by Nuvoton ma35d1"

inherit kernel

# We need to pass it as param since kernel might support more then one
# machine, with different entry points
ma35d1_KERNEL_LOADADDR = "0x80080000"
KERNEL_EXTRA_ARGS += "LOADADDR=${ma35d1_KERNEL_LOADADDR}"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

SRCBRANCH = "linux-5.4.y"
LOCALVERSION = "-${SRCBRANCH}"

KERNEL_SRC ?= "git://github.com/NUA3500/linux-5.4.y.git;protocol=https"
SRC_URI = "${KERNEL_SRC}"

SRC_URI += " \
    file://cfg80211.config \
    "

SRC_URI += "${@bb.utils.contains('DISTRO_FEATURES', '8189es', ' file://8189es.ko', '', d)}"
SRC_URI += "${@bb.utils.contains('DISTRO_FEATURES', '8192es', ' file://8192es.ko', '', d)}"

SRCREV="master"
S = "${WORKDIR}/git"
B = "${WORKDIR}/build"

DEPENDS += "openssl-native util-linux-native libyaml-native"
DEFAULT_PREFERENCE = "1"
# =========================================================================
# Kernel
# =========================================================================
# Kernel image type
KERNEL_IMAGETYPE = "Image"

do_configure_prepend() {
    bbnote "Copying defconfig"
    cp ${S}/arch/${ARCH}/configs/${KERNEL_DEFCONFIG} ${WORKDIR}/defconfig
    cat ${WORKDIR}/cfg80211.config >> ${WORKDIR}/defconfig
}

do_install_append() {
    if [ -e ${WORKDIR}/8189es.ko ]; then
        install -d ${D}/${base_libdir}/modules/${KV}
        install -m 0644 ${WORKDIR}/8189es.ko ${D}/${base_libdir}/modules/${PV}/8189es.ko
    elif [ -e ${WORKDIR}/8192es.ko ]; then
        install -d ${D}/${base_libdir}/modules/${KV}
        install -m 0644 ${WORKDIR}/8192es.ko ${D}/${base_libdir}/modules/${PV}/8192es.ko
    fi
}


COMPATIBLE_MACHINE = "(ma35d1)"
