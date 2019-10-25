#!/bin/bash

set -e

echo '-----BEGIN PGP PUBLIC KEY BLOCK-----
Version: GnuPG v2
' >public.asc
echo ${GPG_PUBLIC_KEY} >>public.asc
echo '-----END PGP PUBLIC KEY BLOCK-----' >>public.asc

echo '-----BEGIN PGP PRIVATE KEY BLOCK-----
Version: GnuPG v2
' >private.asc
echo ${GPG_PRIVATE_KEY} >>private.asc
echo '-----END PGP PRIVATE KEY BLOCK-----' >>private.asc

export HOME=$(pwd)
gpg --batch --import public.asc
echo -n "${GPG_PASSPHRASE}" | gpg --passphrase-fd 0 --batch --import private.asc
rm public.asc
rm private.asc

mvn -pl $(cat pom.xml |grep '<module>' | sed -e 's/.*<module>//' -e 's/<\/module>//' | xargs -i echo -n "{}," | sed -e 's/,$//') deploy
rm -rf .gnupg
